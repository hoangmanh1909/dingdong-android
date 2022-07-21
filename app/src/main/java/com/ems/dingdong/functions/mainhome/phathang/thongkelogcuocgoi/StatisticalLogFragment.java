package com.ems.dingdong.functions.mainhome.phathang.thongkelogcuocgoi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * The ScannerCode Fragment
 */
public class StatisticalLogFragment extends ViewFragment<StatisticalLogContract.Presenter> implements StatisticalLogContract.View, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int URL_LOADER = 1;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    public static StatisticalLogFragment getInstance() {
        return new StatisticalLogFragment();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<StatisticalLogMode> mList = new ArrayList<>();
    StatisticalLogAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statisticallog;
    }


    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void onPause() {
        super.onPause();
//        cameraView.stopCameraPreview();// Stop camera on pause
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume", "OnResume");
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        if (((DingDongActivity) getActivity()).getSupportActionBar() != null)
            ((DingDongActivity) getActivity()).getSupportActionBar().hide();
        getLoaderManager().initLoader(URL_LOADER, null, this);
        mList = new ArrayList<>();
        mAdapter = new StatisticalLogAdapter(getViewContext(), mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, @Nullable Bundle args) {
        Log.d("AAAAAAAA", "onCreateLoader() >> loaderID : " + loaderID);
        switch (loaderID) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor managedCursor) {
        Log.d("AAAAA", "onLoadFinished()");
        try {
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;
                String datea = DateTimeUtils.convertDateToString(callDayTime, DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                int callTypeCode = Integer.parseInt(callType);

                StatisticalLogMode mode = new StatisticalLogMode();
                mode.setCallDate(datea);
                mode.setPhNumber(phNumber);
                mode.setCallDuration(callDuration);
                mode.setCallType(callTypeCode);
                mode.setDate(callDate);
                mList.add(mode);
            }
            Collections.sort(mList, new NameComparator());
            managedCursor.close();
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    class NameComparator implements Comparator<StatisticalLogMode> {
        public int compare(StatisticalLogMode s1, StatisticalLogMode s2) {
            return s1.getDate().compareTo(s2.getDate());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
