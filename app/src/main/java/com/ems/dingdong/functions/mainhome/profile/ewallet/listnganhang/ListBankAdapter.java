package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListBankAdapter extends RecyclerView.Adapter<ListBankAdapter.HolderView> {
    private List<Item> mList;
    private List<Item> mListFilter;
    private Context mContext;

    public ListBankAdapter(Context context, List<Item> items) {
        mList = items;
        mListFilter = items;
        mContext = context;
    }

    public void setListFilter(List<Item> list) {
        mListFilter = list;
    }

    public List<Item> getListFilter() {
        return mListFilter;
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.img_motaikhoan)
        ImageView imgMotaikhoan;
        @BindView(R.id.tv_tennganhang)
        TextView tvTennganhang;
        @BindView(R.id.tv_lienket)
        TextView tvLienket;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            Item item = (Item) model;
//            tvReason.setText(item.getText());
            imgMotaikhoan.setImageResource(item.getImg());
            tvTennganhang.setText(item.getText());
            if (item.isLienket())
                tvLienket.setText("Đã liên kết");
            else {
                if (item.getValue().equals("1")) {
                    tvLienket.setText(mContext.getString(R.string.not_link_to_e_wallet));
                } else
                    tvLienket.setText("Chưa liên kết");
            }

        }
    }
}
