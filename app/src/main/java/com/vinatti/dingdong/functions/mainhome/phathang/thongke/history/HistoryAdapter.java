package com.vinatti.dingdong.functions.mainhome.phathang.thongke.history;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class HistoryAdapter extends RecyclerBaseAdapter {


    public HistoryAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_history));
    }


    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_parcel_code)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_receiver_name)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_ReasonName)
        CustomTextView tvReasonName;
        @BindView(R.id.tv_SolutionName)
        CustomTextView tvSolutionName;
        @BindView(R.id.tv_delivery_date)
        CustomTextView tvDeliveryDate;
        @BindView(R.id.tv_note)
        CustomTextView tvNote;
        @BindView(R.id.tv_status_name)
        CustomBoldTextView tvStatusName;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvParcelCode.setText(item.getParcelCode());
            if (item.isCheckStatus()) {
                tvReceiverName.setText(String.format("Người nhận: %s", item.getReciverName()));
                tvReceiverName.setVisibility(View.VISIBLE);
            } else {
                tvReceiverName.setVisibility(View.GONE);
            }
            if (item.isCheckStatusNo()) {
                tvReasonName.setText(String.format("Lý do: %s", item.getReasonName()));
                tvSolutionName.setText(String.format("Hướng xử lý: %s", item.getSolutionName()));
                tvReasonName.setVisibility(View.VISIBLE);
                tvSolutionName.setVisibility(View.VISIBLE);
            } else {
                tvReasonName.setVisibility(View.GONE);
                tvSolutionName.setVisibility(View.GONE);
            }
            tvDeliveryDate.setText(String.format("Thời gian phát: %s", item.getDeliveryDate()));
            tvStatusName.setText(String.format("Trạng thái: %s", item.getStatusName()));
            tvNote.setText(item.getNote());
            if (item.getStatus().equals("C14")) {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tvStatusName.setTextColor(mContext.getResources().getColor(R.color.color_605e60));
            }
        }
    }
}
