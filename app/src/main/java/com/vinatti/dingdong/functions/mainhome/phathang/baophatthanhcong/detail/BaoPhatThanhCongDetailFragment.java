package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.detail;

import android.widget.LinearLayout;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.base.DingDongActivity;
import com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild.PhonePresenter;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import butterknife.BindView;

/**
 * The BaoPhatThanhCongDetail Fragment
 */
public class BaoPhatThanhCongDetailFragment extends ViewFragment<BaoPhatThanhCongDetailContract.Presenter> implements BaoPhatThanhCongDetailContract.View {

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
        tvCollectAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
        tvReceiveCollectFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getReceiveCollectFee()))));
        tvAmountTotal.setText(String.format("%s đ", (NumberUtils.formatPriceNumber(Long.parseLong(item.getReceiveCollectFee()) + Long.parseLong(item.getCollectAmount())))));
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


}
