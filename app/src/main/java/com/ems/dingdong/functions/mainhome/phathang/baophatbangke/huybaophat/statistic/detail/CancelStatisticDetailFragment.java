package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail;

import android.view.View;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.mapbox.core.utils.TextUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CancelStatisticDetailFragment extends ViewFragment<CancelStatisticDetailContract.Presenter>
        implements CancelStatisticDetailContract.View {

    @BindView(R.id.tv_parcel_code)
    CustomBoldTextView tvParcelCode;
    @BindView(R.id.tv_receiver_name)
    CustomTextView tvReceiverName;
    @BindView(R.id.tv_receiver_address)
    CustomTextView tvReceiverAddress;
    @BindView(R.id.tv_sender_name)
    CustomTextView tvSenderName;
    @BindView(R.id.tv_sender_address)
    CustomTextView tvSenderAddress;
    @BindView(R.id.tv_debit_status)
    CustomTextView tvDebitStatus;
    @BindView(R.id.tv_cancel_status)
    CustomTextView tvCancelStatus;
    @BindView(R.id.tv_cod)
    CustomTextView tvCod;
    @BindView(R.id.tv_fee)
    CustomTextView tvFee;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_statistic_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter != null) {
            CancelStatisticItem item = mPresenter.getItem();
            if (!TextUtils.isEmpty(item.getLadingCode())) {
                tvParcelCode.setText(item.getLadingCode());
            }

            if (!TextUtils.isEmpty(item.getReceiverName())) {
                tvReceiverName.setText(item.getReceiverName());
            }

            if (!TextUtils.isEmpty(item.getReceiverAddress())) {
                tvReceiverAddress.setText(item.getReceiverAddress());
            }

            if (!TextUtils.isEmpty(item.getSenderName())) {
                tvSenderName.setText(item.getSenderName());
            }

            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvCancelStatus.setText(item.getStatusName());
                if (getString(R.string.not_yet_appproved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvCancelStatus.setTextColor(getResources().getColor(R.color.blue));
                } else if (getString(R.string.approved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvCancelStatus.setTextColor(getResources().getColor(R.color.orange));
                } else {
                    tvCancelStatus.setTextColor(getResources().getColor(R.color.red_light));
                }
            }

            if (item.getFee() != null) {
                tvFee.setText(String.valueOf(item.getFee()));
            }

            if (item.getcODAmount() != null) {
                tvCod.setText(String.valueOf(item.getcODAmount()));
            }

            if (item.getPaymentPayPostStatus() != null) {
                if ("Y".equals(item.getPaymentPayPostStatus())) {
                    tvDebitStatus.setText("Gạch nợ thành công");
                } else if ("N".equals(item.getPaymentPayPostStatus())) {
                    tvDebitStatus.setText("Gạch nợ thất bại");
                }
            }
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    public static CancelStatisticDetailFragment getInstance() {
        return new CancelStatisticDetailFragment();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mPresenter.back();
                break;
            default:
                throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }
}
