package com.vinatti.dingdong.functions.mainhome.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.functions.mainhome.phathang.thongke.history.HistoryAdapter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.StatusInfo;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.form.FormItemEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The Location Fragment
 */
public class LocationFragment extends ViewFragment<LocationContract.Presenter> implements LocationContract.View {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    @BindView(R.id.ll_scan_qr)
    RelativeLayout llScanQr;
    @BindView(R.id.tv_parcelCode)
    CustomTextView tvParcelCode;
    @BindView(R.id.tv_SenderName)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_receiverName)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_receiverAddress)
    CustomTextView tvReceiverAddress;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.ll_status)
    View llStatus;
    @BindView(R.id.edt_ladingCode)
    FormItemEditText edtLadingCode;
    @BindView(R.id.img_search)
    View imgSearch;
    private ArrayList<StatusInfo> mList;
    private StatusAdapter mAdapter;

    public static LocationFragment getInstance() {
        return new LocationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();
        mList = new ArrayList<>();
        mAdapter = new StatusAdapter(getActivity(), mList);
        RecyclerUtils.setupVerticalRecyclerView(getActivity(), recycler);
        recycler.setAdapter(mAdapter);
        edtLadingCode.getEditText().setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_CHARACTERS);
    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (getActivity() != null) {
            if (((DingDongActivity) getActivity()).getSupportActionBar() != null) {
                ((DingDongActivity) getActivity()).getSupportActionBar().show();
            }
        }
    }

    public void getQuery(String ladingCode) {
        mPresenter.findLocation(ladingCode.toUpperCase());
    }

    @OnClick({R.id.ll_scan_qr, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {
                        getQuery(value.replace("+", ""));
                    }
                });
                break;
            case R.id.img_search:
                if (TextUtils.isEmpty(edtLadingCode.getText())) {
                    Toast.showToast(getActivity(), "Vui lòng nhập mã");
                    return;
                }
                getQuery(edtLadingCode.getText());
                break;
        }

    }

    @Override
    public void showFindLocationSuccess(CommonObject commonObject) {
        llDetail.setVisibility(View.VISIBLE);
        tvParcelCode.setText(commonObject.getCode());
        tvSenderName.setText(commonObject.getSenderName());
        tvSenderAddress.setText(commonObject.getSenderAddress());
        tvReceiverName.setText(commonObject.getReciverName());
        tvReceiverAddress.setText(commonObject.getReceiverAddress());
        if (commonObject.getStatus() == null || commonObject.getStatus().isEmpty()) {
            llStatus.setVisibility(View.GONE);
        } else {
            mList = commonObject.getStatus();
            mAdapter.addItems(commonObject.getStatus());
            llStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showEmpty() {
        llDetail.setVisibility(View.GONE);
    }
}
