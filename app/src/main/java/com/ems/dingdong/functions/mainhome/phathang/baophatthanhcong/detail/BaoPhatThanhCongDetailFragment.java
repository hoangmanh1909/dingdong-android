package com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.detail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BaoPhatThanhCongDetail Fragment
 */
public class BaoPhatThanhCongDetailFragment extends ViewFragment<BaoPhatThanhCongDetailContract.Presenter> implements BaoPhatThanhCongDetailContract.View {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    private static final String TAG = BaoPhatThanhCongDetailFragment.class.getSimpleName();
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

    public static BaoPhatThanhCongDetailFragment getInstance() {
        return new BaoPhatThanhCongDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bao_phat_thanh_cong_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        CommonObject item = mPresenter.getBaoPhatThanhCong();
        tvParcelCode.setText(item.getParcelCode());
        if (!TextUtils.isEmpty(item.getCollectAmount()))
            tvCollectAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
        else {
            tvCollectAmount.setText("0 đ");
        }
        if (!TextUtils.isEmpty(item.getReceiveCollectFee()))
            tvReceiveCollectFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getReceiveCollectFee()))));
        else {
            tvReceiveCollectFee.setText("0 đ");
        }
        if (!TextUtils.isEmpty(item.getCollectAmount()) && !TextUtils.isEmpty(item.getReceiveCollectFee()))
            tvAmountTotal.setText(String.format("%s đ", (NumberUtils.formatPriceNumber(Long.parseLong(item.getReceiveCollectFee()) + Long.parseLong(item.getCollectAmount())))));
        else {
            tvAmountTotal.setText("0 đ");
        }
        if (!TextUtils.isEmpty(item.getCollectAmount()) && TextUtils.isEmpty(item.getReceiveCollectFee()))
            tvAmountTotal.setText(String.format("%s đ", (NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount())))));

        tvSenderName.setText(item.getSenderName());
        tvSenderAddress.setText(item.getSenderAddress());
        tvSenderPhone.setText(item.getSenderPhone());
        tvReceiverName.setText(item.getReceiverName());
        tvReceiverAddress.setText(item.getReceiverAddress());
        tvReceiverIDNumber.setText(item.getReceiverIDNumber());
        String[] phones = item.getReceiverPhone().split(",");
        for (int i = 0; i < phones.length; i++) {
            if (!phones[i].isEmpty()) {
                getChildFragmentManager().beginTransaction()
                        .add(R.id.ll_contact,
                                new PhonePresenter((ContainerView) getActivity())
                                        .setPhone(phones[i].trim())
                                        .getFragment(), TAG + i)
                        .commit();
            }
        }
        checkPermissionCall();

    }

    private void checkPermissionCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasReadExternalPermission = getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE);
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
                ((DingDongActivity) getActivity()).getSupportActionBar().hide();
            }
        }
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        mPresenter.back();
    }
}
