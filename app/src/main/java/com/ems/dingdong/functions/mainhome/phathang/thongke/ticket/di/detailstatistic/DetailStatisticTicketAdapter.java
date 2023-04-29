package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.di.detailstatistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.STTTicketManagementTotalRespone;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailStatisticTicketAdapter extends RecyclerView.Adapter<DetailStatisticTicketAdapter.HolderView> implements Filterable {

    List<STTTicketManagementTotalRespone> mListFilter;
    List<STTTicketManagementTotalRespone> mList;
    Context mContext;

    public DetailStatisticTicketAdapter(Context context, List<STTTicketManagementTotalRespone> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<STTTicketManagementTotalRespone> filteredList = new ArrayList<>();
                    for (STTTicketManagementTotalRespone row : mList) {
                        if (row.getTicketCode().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getLadingCode().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<STTTicketManagementTotalRespone>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistic_ticket, parent, false));
    }

    protected class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mabg)
        TextView tvMabg;
        @BindView(R.id.tv_ticket)
        TextView tvTicket;
        @BindView(R.id.tv_thoi_gian)
        TextView tvThoiGian;
        @BindView(R.id.tv_trangthai)
        TextView tv_trangthai;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bindView(Object model, int position) {
            STTTicketManagementTotalRespone item = (STTTicketManagementTotalRespone) model;
            tvMabg.setText(position + 1 + ". " + item.getLadingCode());
            tvTicket.setText(item.getTicketCode() + "");
            tvThoiGian.setText(item.getCreatedDate());
            tv_trangthai.setText(item.getStatusName());

        }
    }
}
