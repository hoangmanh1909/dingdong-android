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
import com.ems.dingdong.utiles.NumberUtils;

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
        @BindView(R.id.tv_sotaikhoan)
        TextView tvSotaikhoan;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_matdinh)
        TextView tvMatdinh;


        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            Item item = (Item) model;
            imgMotaikhoan.setImageResource(item.getImg());
            tvTitle.setText(item.getText());

            tvSotaikhoan.setText(item.getSotaikhoan());
            if (item.isLienket())
                tvMatdinh.setText("Đã liên kết");
            else tvMatdinh.setText("Chờ xác nhận");

            if (NumberUtils.isNumber(item.getSotaikhoan())) {
                String mahoa = item.getSotaikhoan().substring(item.getSotaikhoan().length() - 4, item.getSotaikhoan().length());
                mahoa = "xxxx xxxx " + mahoa;
                tvSotaikhoan.setText(mahoa);
            }

        }
    }
}
