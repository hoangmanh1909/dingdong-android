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
        @BindView(R.id.tv_Description)
        CustomTextView tvDescription;
        @BindView(R.id.tv_TypeMessage)
        CustomTextView tvTypeMessage;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            StatusInfo item = (StatusInfo) model;
            if (!TextUtils.isEmpty(item.getPOName())) {
                tvPOCodePOName.setText(String.format("%s - %s", item.getPOCode(), item.getPOName()));
                tvPOCodePOName.setVisibility(View.VISIBLE);
            } else {
                tvPOCodePOName.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getStatusMessage())) {
                tvStatusMessage.setText(item.getStatusMessage());
                tvStatusMessage.setVisibility(View.VISIBLE);
            } else {
                tvStatusMessage.setVisibility(View.INVISIBLE);
            }
            if (item.getStatusDate() == null) {
                item.setStatusDate("");
            }
            if (item.getStatusTime() == null) {
                item.setStatusTime("");
            }
            if (!TextUtils.isEmpty(item.getDescription())) {
                tvDescription.setText(item.getDescription());
                tvDescription.setVisibility(View.VISIBLE);
            } else {
                tvDescription.setText("");
                tvDescription.setVisibility(View.GONE);
            }
            tvTypeMessage.setText("");
            if (!TextUtils.isEmpty(item.getActionTypeName())) {
                tvTypeMessage.setText(item.getActionTypeName());
                tvTypeMessage.setVisibility(View.VISIBLE);
            } else {
                tvTypeMessage.setText("");
                tvTypeMessage.setVisibility(View.GONE);
            }
            tvStatusDateStatusTime.setText(String.format("%s, %s", item.getStatusDate(), item.getStatusTime()));
        }
    }
}
