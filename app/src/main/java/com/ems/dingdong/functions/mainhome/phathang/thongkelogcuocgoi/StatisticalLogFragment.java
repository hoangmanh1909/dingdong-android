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
import com.ems.dingdong.utiles.DateTimeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
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
        checkSelfPermission();
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

        StringBuilder sb = new StringBuilder();

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        sb.append("<h4>Call Log Details <h4>");
        sb.append("\n");
        sb.append("\n");

        sb.append("<table>");
        Log.d("AAAAAA", new Gson().toJson(managedCursor));
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            String datea = DateTimeUtils.convertDateToString(callDayTime, DateTimeUtils.DEFAULT_DATETIME_FORMAT4);
            int callTypeCode = Integer.parseInt(callType);

            StatisticalLogMode mode = new StatisticalLogMode();
            mode.setCallDate(datea);
            mode.setPhNumber(phNumber);
            mode.setCallDuration(callDuration);
            mList.add(mode);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Gọi đi";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Gọi đến";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Nhỡ";
                    break;
            }
            mode.setCallType(dir);

            sb.append("<tr>")
                    .append("<td>Số điện thoại: </td>")
                    .append("<td><strong>")
                    .append(phNumber)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Loại cuộc gọi:</td>")
                    .append("<td><strong>")
                    .append(dir)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Date & Time:</td>")
                    .append("<td><strong>")
                    .append(datea)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Call Duration (Seconds):</td>")
                    .append("<td><strong>")
                    .append(callDuration)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<br/>");
        }
        sb.append("</table>");

        managedCursor.close();
        mAdapter.notifyDataSetChanged();
//        callLogsTextView.setText(Html.fromHtml(sb.toString()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
