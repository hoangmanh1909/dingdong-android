package com.ems.dingdong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.addticket.SolutionMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemCodeAdapter extends RecyclerView.Adapter<ItemCodeAdapter.HolderView> {

    public List<SolutionMode> mListFilter;
    List<SolutionMode> mList;
    Context mContext;

    public ItemCodeAdapter(Context context, List<SolutionMode> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCodeAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));

    }

    @Override
    public ItemCodeAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCodeAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_v1, parent, false));
    }


    public class HolderView extends RecyclerView.ViewHolder {

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public SolutionMode getItem(int position) {
            return mListFilter.get(position);
        }


        @BindView(R.id.tv_noidung)
        public TextView tvNoidung;
        @BindView(R.id.cb_check)
        public ImageView cbCheck;

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        public void bindView(Object model) {
            SolutionMode item = (SolutionMode) model;
            tvNoidung.setText(item.getName());
            if (item.isIs())
                cbCheck.setVisibility(View.VISIBLE);
            else cbCheck.setVisibility(View.GONE);
        }
    }
}