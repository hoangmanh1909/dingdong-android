package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.Typefaces;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuuguiAdapter extends RecyclerView.Adapter<BuuguiAdapter.HolderView> {

    List<ParcelCodeInfo> mListFilter;
    List<ParcelCodeInfo> mList;
    Context mContext;

    public BuuguiAdapter(Context context, List<ParcelCodeInfo> items) {
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
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parcel_list_common, parent, false));
    }

    class HolderView extends RecyclerView.ViewHolder {
        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ParcelCodeInfo getItem(int position) {
            return mListFilter.get(position);
        }

        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_sodonhang)
        TextView tvSodonhang;
        @BindView(R.id.cb_selected)
        public CheckBox cbSelected;


        @SuppressLint("SetTextI18n")
        public void bindView(Object model) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            cbSelected.setVisibility(View.GONE);
            if (item.getOrderNumber().isEmpty()) {
                tvSodonhang.setVisibility(View.GONE);
            } else tvSodonhang.setVisibility(View.VISIBLE);
            tvCode.setVisibility(View.VISIBLE);
            tvCode.setText(item.getTrackingCode());
            tvSodonhang.setText(item.getOrderNumber());
        }
    }
}
