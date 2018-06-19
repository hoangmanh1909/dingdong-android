package com.vinatti.dingdong.functions.mainhome.phathang.thongke.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class StatictisAdapter extends RecyclerBaseAdapter {


    public StatictisAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statictis));
    }


    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_count)
        CustomBoldTextView tvCount;
        @BindView(R.id.tv_parcel_code)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_receiver_name)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.tv_delivery_date)
        CustomTextView tvDeliveryDate;
        @BindView(R.id.tv_reason)
        CustomTextView tvReason;
        @BindView(R.id.tv_status_name)
        CustomBoldTextView tvStatusName;
        @BindView(R.id.tv_amount)
        CustomBoldTextView tvAmount;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvCount.setText(String.format("Số thứ tự: %s", item.getCount()));
            tvParcelCode.setText(item.getParcelCode());
            tvReceiverName.setText(String.format("%s - %s", item.getReciverName(), item.getReceiverPhone()));
            tvReceiverAddress.setText(item.getReceiverAddress());
            tvDeliveryDate.setText(DateTimeUtils.formatDate(item.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5, DateTimeUtils.SIMPLE_DATE_FORMAT));
            tvStatusName.setText(item.getStatusName());
            tvReason.setText(item.getReasonName());
            if (item.getStatus().equals("C14")) {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.color_000080));
            }

            if (!TextUtils.isEmpty(item.getCollectAmount())) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            } else {
                tvAmount.setText(String.format("%s VNĐ", "0"));
            }
        }
    }
}
