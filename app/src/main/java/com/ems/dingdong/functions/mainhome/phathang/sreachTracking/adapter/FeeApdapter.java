package com.ems.dingdong.functions.mainhome.phathang.sreachTracking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeeApdapter extends RecyclerView.Adapter<FeeApdapter.HolderView> {

    List<Item> mListFilter;
    List<Item> mList;
    Context mContext;

    public FeeApdapter(Context context, List<Item> items) {
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
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fee_, parent, false));
    }

    class HolderView extends RecyclerView.ViewHolder {


        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Item getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_lading)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_monney)
        AppCompatTextView tvFee;


        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            Item item = (Item) model;

            tvTitle.setText(item.getValue()+"");
            tvFee.setText(item.getText()+"");
        }
    }
}
