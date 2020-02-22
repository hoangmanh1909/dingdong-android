package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

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
        @BindView(R.id.tv_status_paypost)
        CustomTextView tvStatusPaypost;
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
            if (!TextUtils.isEmpty(item.getReceiverPhone())) {
                tvReceiverName.setText(String.format("%s - %s", item.getReciverName(), item.getReceiverPhone()));
            } else {
                tvReceiverName.setText(String.format("%s", item.getReciverName()));
            }
            if("".equals(item.getIsPaypost()) || item.getIsPaypost() == null)
            {
                tvStatusPaypost.setText("");
            }
           else if ("Y".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("Gạch nợ thành công");
                tvStatusPaypost.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } /*else if ("E".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("Gạch nợ thất bại");
                tvStatusPaypost.setTextColor(mContext.getResources().getColor(R.color.red_light));
            } else if ("N".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("Bưu gửi ko gạch nợ");
            } else if ("A".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("N/A");
            } else if ("L".equals(item.getIsPaypost())) {
                tvStatusPaypost.setText("98");
            }*/ else {
                tvStatusPaypost.setText("Gạch nợ thất bại");
                tvStatusPaypost.setTextColor(mContext.getResources().getColor(R.color.color_debit_unsuccessful));
            }

            // tvDeliveryDate.setText(DateTimeUtils.formatDate(item.getDeliveryDate(), DateTimeUtils.SIMPLE_DATE_FORMAT5, DateTimeUtils.SIMPLE_DATE_FORMAT));
            tvDeliveryDate.setText(item.getDeliveryDate());
            tvStatusName.setText(item.getStatusName());
            if (item.getStatus().equals("C14")) {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                if (!TextUtils.isEmpty(item.getServiceName())) {
                    tvReason.setText(String.format("%s", item.getServiceName()));
                }
            } else {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.color_000080));
                if (!TextUtils.isEmpty(item.getReasonName())) {
                    tvReason.setText(String.format("%s", item.getReasonName()));
                }
            }

            if (!TextUtils.isEmpty(item.getCollectAmount())) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            } else {
                tvAmount.setText(String.format("%s VNĐ", "0"));
            }
        }
    }
}
