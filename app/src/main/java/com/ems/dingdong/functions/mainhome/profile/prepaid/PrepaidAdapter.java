package com.ems.dingdong.functions.mainhome.profile.prepaid;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryPrepaidResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrepaidAdapter extends RecyclerView.Adapter<PrepaidAdapter.HolderView> {

    private List<HistoryPrepaidResponse> mList;
    private Context mContext;
    public PrepaidAdapter(@NonNull Context context, List<HistoryPrepaidResponse> items) {
        mList = items;
        mContext = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_prepaid, parent, false));
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

        @BindView(R.id.tv_lading)
        CustomTextView ladingCode;

        @BindView(R.id.amount)
        CustomTextView amount;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bindView(HistoryPrepaidResponse item) {
            if (item.getAmount() != null) {
                amount.setText(String.format("Số tiền: %s đ", NumberUtils.formatPriceNumber(item.getAmount())));
            }

            if (!TextUtils.isEmpty(item.getLadingCode())) {
                ladingCode.setText(String.format("Mã bưu gửi: %s đ", item.getLadingCode()));
            }
        }
    }
}
