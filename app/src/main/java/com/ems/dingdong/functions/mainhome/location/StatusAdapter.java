package com.ems.dingdong.functions.mainhome.location;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatusInfo;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomMediumTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class StatusAdapter extends RecyclerBaseAdapter {


    public StatusAdapter(Context context, List<StatusInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_status));
    }


    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_POCode_POName)
        CustomBoldTextView tvPOCodePOName;
        @BindView(R.id.tv_StatusMessage)
        CustomTextView tvStatusMessage;
        @BindView(R.id.tv_StatusDate_StatusTime)
        CustomTextView tvStatusDateStatusTime;
        @BindView(R.id.tv_reason)
        CustomTextView tvReason;
        @BindView(R.id.tv_StatusCode)
        CustomMediumTextView tvStatusCode;
        @BindView(R.id.tv_StatusTime)
        CustomTextView tvStatusTime;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            StatusInfo item = (StatusInfo) model;
            tvPOCodePOName.setText(String.format("%s - %s", item.getPOCode(), item.getPOName()));
            if (!TextUtils.isEmpty(item.getStatusMessage())) {
                tvStatusMessage.setText(item.getStatusMessage());
                tvStatusMessage.setVisibility(View.VISIBLE);
            } else {
                tvStatusMessage.setVisibility(View.GONE);
            }
            if (item.getStatusTime() == null) {
                item.setStatusTime("");
            }
            if (item.getStatusDate() == null) {
                item.setStatusDate("");
            }
            tvStatusDateStatusTime.setText(String.format("%s %s", item.getStatusDate(), item.getStatusTime()));
            if (item.getStatusCode().equals("C14")) {
                tvStatusCode.setText("Phát thành công");

            } else {
                tvStatusCode.setText("Phát không thành công");
            }
            tvStatusTime.setText(item.getStatusTime());
            tvReason.setText(item.getReasonCode());
        }
    }
}
