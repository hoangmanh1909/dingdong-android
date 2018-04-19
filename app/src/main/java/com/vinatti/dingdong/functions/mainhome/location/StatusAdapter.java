package com.vinatti.dingdong.functions.mainhome.location;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.StatusInfo;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

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

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            StatusInfo item = (StatusInfo) model;
            tvPOCodePOName.setText(String.format("%s - %s", item.getPOCode(), item.getPOName()));
            tvStatusMessage.setText(item.getStatusMessage());
            tvStatusDateStatusTime.setText(String.format("%s %s", item.getStatusDate(), item.getStatusTime()));
        }
    }
}
