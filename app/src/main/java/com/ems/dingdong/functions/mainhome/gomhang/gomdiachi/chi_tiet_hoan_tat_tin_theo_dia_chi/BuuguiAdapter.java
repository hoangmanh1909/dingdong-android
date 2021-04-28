package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiAdapter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.CreateBd13Adapter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuuguiAdapter extends RecyclerView.Adapter<BuuguiAdapter.HolderView> implements Filterable {

    Activity activity;
    private List<ParcelCodeInfo> mListFilter;
    private List<ParcelCodeInfo> mList;
    Context mContext;
    private final int limit = 5;

    public BuuguiAdapter(Context context, List<ParcelCodeInfo> list) {
        activity = (Activity) context;
        mContext = context;
        mList = list;
        mListFilter = list;
    }

    @Override
    public BuuguiAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BuuguiAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoan_tat_tin_theo_dia_chi, parent, false));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public List<ParcelCodeInfo> getListFilter() {
        return mListFilter;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
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
                    List<ParcelCodeInfo> filteredList = new ArrayList<>();
                    for (ParcelCodeInfo row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTrackingCode().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getOrderCode().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (List<ParcelCodeInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_mabuugui)
        TextView tvMabuugui;
        @BindView(R.id.tv_matin)
        TextView tvMatin;
        @BindView(R.id.radio_btn)
        public CheckBox radioBtn;

        @BindView(R.id.ll_background)
        LinearLayout ll_backgroundl;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ParcelCodeInfo getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            tvMabuugui.setText(item.getTrackingCode());
            tvMatin.setText(item.getOrderCode());
            radioBtn.setOnCheckedChangeListener((v1, v2) -> {
                if (v2) {
                    ll_backgroundl.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    ll_backgroundl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });
            radioBtn.setChecked(item.isSelected());



//            if (position == 0) {
//                radioBtn.setVisibility(View.VISIBLE);
//            } else {
//                ParcelCodeInfo info = (ParcelCodeInfo) mItems.get(position - 1);
//                String prevDate = info.getOrderCode().split("\\s")[0];
//                if (compareDate(model.getOrderCode(), prevDate) == 1) {
//                    radioBtn.setVisibility(View.INVISIBLE);
//                } else {
//                    radioBtn.setVisibility(View.VISIBLE);
//                }
//            }
        }


    }
}
