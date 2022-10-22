package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDichvu extends RecyclerView.Adapter<ItemDichvu.HolderView> {

    public List<Item> mListFilter;
    List<Item> mList;
    Context mContext;

    public ItemDichvu(Context context, List<Item> items) {
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
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoigui, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Item getItem(int position) {
            return mListFilter.get(position);
        }


        @BindView(R.id.tv_noidung)
        TextView tvNoidung;
        @BindView(R.id.cb_check)
        public ImageView cbCheck;

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        public void bindView(Object model) {
            Item item = (Item) model;
            tvNoidung.setText(item.getText());
            if (item.isIs())
                cbCheck.setVisibility(View.VISIBLE);
            else cbCheck.setVisibility(View.GONE);
        }
    }
}
