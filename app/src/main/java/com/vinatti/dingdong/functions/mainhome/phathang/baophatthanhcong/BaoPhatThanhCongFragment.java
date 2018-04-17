package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.core.base.viper.ViewFragment;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * The BaoPhatThanhCong Fragment
 */
public class BaoPhatThanhCongFragment extends ViewFragment<BaoPhatThanhCongContract.Presenter> implements BaoPhatThanhCongContract.View {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    @BindView(R.id.tv_ParcelCode)
    CustomTextView tvParcelCode;
    @BindView(R.id.tv_CollectAmount)
    CustomTextView tvCollectAmount;
    @BindView(R.id.tv_ReceiveCollectFee)
    CustomTextView tvReceiveCollectFee;
    @BindView(R.id.tv_amountTotal)
    CustomBoldTextView tvAmountTotal;
    @BindView(R.id.tv_SenderName)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_SenderAddress)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_SenderPhone)
    CustomTextView tvSenderPhone;
    @BindView(R.id.tv_ReceiverName)
    CustomTextView tvReceiverName;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.tv_ReceiverAddress)
    CustomTextView tvReceiverAddress;
    @BindView(R.id.tv_ReceiverIDNumber)
    CustomTextView tvReceiverIDNumber;
    @BindView(R.id.btn_confirm)
    CustomTextView btnConfirm;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    public static BaoPhatThanhCongFragment getInstance() {
        return new BaoPhatThanhCongFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_thanh_cong;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        checkSelfPermission();

    }

    protected void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);
            if (hasReadExternalPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }

        }
    }

    public void getQuery(String query) {

    }


    @OnClick({R.id.btn_confirm, R.id.ll_scan_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_scan_qr:
                mPresenter.showBarcode(new BarCodeCallback() {
                    @Override
                    public void scanQrcodeResponse(String value) {

                    }
                });
                break;
            case R.id.btn_confirm:
                break;
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        if (((DingDongActivity) getActivity()).getSupportActionBar() != null)
            ((DingDongActivity) getActivity()).getSupportActionBar().show();
    }
}
