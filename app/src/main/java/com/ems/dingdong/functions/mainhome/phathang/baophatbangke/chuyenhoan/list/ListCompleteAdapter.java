package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuyenhoan.list;

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
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCompleteAdapter extends RecyclerView.Adapter<ListCompleteAdapter.HolderView> implements Filterable {

    List<LadingRefundDetailRespone> mListFilter;
    List<LadingRefundDetailRespone> mList1;
    Context mContext;

    public ListCompleteAdapter(Context context, List<LadingRefundDetailRespone> items) {
        mContext = context;
        mList1 = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ListCompleteAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d("ASDASDASD", charString);
                Log.d("ASDASDASD", new Gson().toJson(mList1));
                if (charString.isEmpty()) {
                    mListFilter = mList1;
                } else {
                    List<LadingRefundDetailRespone> filteredList = new ArrayList<>();
                    for (LadingRefundDetailRespone row : mList1) {
                        if (row.getReceiverAddress().toLowerCase().contains(charString.toLowerCase()) ||
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
                try {
                    mListFilter = (ArrayList<LadingRefundDetailRespone>) filterResults.values;
                    notifyDataSetChanged();
                } catch (Exception e) {
                    mListFilter = new ArrayList<>();
                    mListFilter.addAll(mList1);
                    notifyDataSetChanged();
                }

            }
        };
    }

    @Override
    public ListCompleteAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListCompleteAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistic_ticket, parent, false));
    }

    class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mabg)
        TextView tvMabg;
        @BindView(R.id.tv_ticket)
        TextView tvDiaChi;
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
            LadingRefundDetailRespone item = (LadingRefundDetailRespone) model;
            tvMabg.setText(position + 1 + ". " + item.getLadingCode());
            tvDiaChi.setText(item.getReceiverAddress() + "");
            tvThoiGian.setText(item.getDeliveryDate());
            tv_trangthai.setText(item.getTrangThai());
            if (item.getTrangThai().equals("N"))
                tv_trangthai.setText("Nhập chuyển hoàn");
            else tv_trangthai.setText("Đã nhập chuyển hoàn");
        }
    }
}
