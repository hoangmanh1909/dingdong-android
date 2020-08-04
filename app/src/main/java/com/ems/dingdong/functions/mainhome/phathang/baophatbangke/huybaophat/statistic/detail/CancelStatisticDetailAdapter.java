package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.views.CustomTextView;
import com.mapbox.core.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancelStatisticDetailAdapter extends RecyclerView.Adapter<CancelStatisticDetailAdapter.HolderView> {

    private List<CancelStatisticItem> mList;
    private Context mContext;

    CancelStatisticDetailAdapter(Context context, List<CancelStatisticItem> items) {
        mList = items;
        mContext = context;
    }

    public List<CancelStatisticItem> getItemsSelected() {
        List<CancelStatisticItem> commonObjectsSelected = new ArrayList<>();
        List<CancelStatisticItem> items = mList;
        for (CancelStatisticItem item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public void setListFilter(List<CancelStatisticItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CancelStatisticDetailAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancel_history_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date_time)
        CustomTextView tvDateTime;
        @BindView(R.id.tv_cancel_status)
        CustomTextView tvCancelStatus;
        @BindView(R.id.tv_reason)
        CustomTextView tvReason;
        @BindView(R.id.tv_cause)
        CustomTextView tvCause;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public CancelStatisticItem getItem(int position) {
            return mList.get(position);
        }

        public void bindView(Object model) {
            CancelStatisticItem item = (CancelStatisticItem) model;
            if (!TextUtils.isEmpty(item.getLastDateTimeUpdate()))
                tvDateTime.setText(item.getLastDateTimeUpdate());
            else
                tvDateTime.setText("");
            if (!TextUtils.isEmpty(item.getStatusName())) {
                tvCancelStatus.setText(item.getStatusName());
                if (mContext.getString(R.string.not_yet_appproved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvCancelStatus.setTextColor(mContext.getResources().getColor(R.color.grey));
                } else if (mContext.getString(R.string.approved).toUpperCase().equals(item.getStatusName().toUpperCase())) {
                    tvCancelStatus.setTextColor(mContext.getResources().getColor(R.color.bg_primary));
                } else {
                    tvCancelStatus.setTextColor(mContext.getResources().getColor(R.color.red_light));
                }
            } else {
                tvCancelStatus.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(item.getCancelReason())) {
                tvReason.setText(String.format(mContext.getString(R.string.cancel_reason) + ": %s", item.getCancelReason()));
            } else {
                tvReason.setText(mContext.getString(R.string.cancel_reason));
                tvReason.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(item.getReasonTypeName())) {
                tvCause.setText(String.format(mContext.getString(R.string.cancel_causal) + ": %s", item.getReasonTypeName()));
            } else {
                tvCause.setText(mContext.getString(R.string.cancel_causal));
                tvCause.setVisibility(View.GONE);
            }
        }
    }

}
