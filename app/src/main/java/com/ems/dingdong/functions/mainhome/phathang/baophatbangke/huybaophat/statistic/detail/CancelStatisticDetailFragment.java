package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.utils.RecyclerUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.mapbox.core.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_cod)
    CustomTextView tvCod;
    @BindView(R.id.tv_fee)
    CustomTextView tvFee;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_statistic_detail;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter != null) {
            List<CancelStatisticItem> mListStatus = mPresenter.getItems();
            CancelStatisticItem item = mListStatus.get(0);
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

            if (!TextUtils.isEmpty(item.getSenderAddress())) {
                tvSenderAddress.setText(item.getSenderAddress());
            }

            if (item.getFee() != null) {
                tvFee.setText(String.format("%s đ", NumberUtils.formatPriceNumber(item.getFee())));
            } else {
                tvFee.setText("0 đ");
            }

            if (item.getcODAmount() != null) {
                tvCod.setText(String.format("%s đ", NumberUtils.formatPriceNumber(item.getcODAmount())));
            } else {
                tvCod.setText("0 đ");
            }

            if (!TextUtils.isEmpty(item.getPaymentPayPostStatus())) {
                if ("Y".equals(item.getPaymentPayPostStatus())) {
                    tvDebitStatus.setText(getString(R.string.success));
                    tvDebitStatus.setTextColor(getViewContext().getResources().getColor(R.color.bg_primary));
                } else {
                    tvDebitStatus.setText(getString(R.string.not_success));
                    tvDebitStatus.setTextColor(getViewContext().getResources().getColor(R.color.red_light));
                }
            }

            CancelStatisticDetailAdapter mAdapter = new CancelStatisticDetailAdapter(getViewContext(), new ArrayList<>());
            RecyclerUtils.setupVerticalRecyclerView(getViewContext(), recyclerView);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setListFilter(mListStatus);
            mAdapter.notifyDataSetChanged();
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
        if (view.getId() == R.id.img_back) {
            mPresenter.back();
        } else {
            throw new IllegalArgumentException("cant not find view just have clicked");
        }
    }
}
