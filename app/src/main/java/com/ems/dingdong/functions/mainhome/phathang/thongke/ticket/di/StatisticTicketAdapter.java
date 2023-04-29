package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticTicketAdapter extends RecyclerView.Adapter<StatisticTicketAdapter.HolderView> {

    List<STTTicketManagementTotalRespone> mListFilter;
    List<STTTicketManagementTotalRespone> mList;
    Context mContext;

    public StatisticTicketAdapter(Context context, List<STTTicketManagementTotalRespone> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticTicketAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public StatisticTicketAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatisticTicketAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistic_ticket_dateail, parent, false));
    }

    class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_soluong)
        TextView tvSoluong;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            STTTicketManagementTotalRespone item = (STTTicketManagementTotalRespone) model;
            tvTitle.setText(item.getStatusName());
            tvSoluong.setText(item.getQuantity() + "");

        }
    }
}
