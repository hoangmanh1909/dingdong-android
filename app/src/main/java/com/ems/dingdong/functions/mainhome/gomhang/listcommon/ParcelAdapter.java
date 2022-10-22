package com.ems.dingdong.functions.mainhome.gomhang.listcommon;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

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

import java.util.List;

import butterknife.BindView;

public class ParcelAdapter extends RecyclerBaseAdapter {

    public ParcelAdapter(Context context, List<ParcelCodeInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_parcel_list_common));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_sodonhang)
        TextView tvSodonhang;
        @BindView(R.id.cb_selected)
        public CheckBox cbSelected;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            if (item.getOrderNumber().isEmpty()){
                tvSodonhang.setVisibility(View.GONE);
            }else tvSodonhang.setVisibility(View.VISIBLE);
            tvCode.setText(item.getTrackingCode());
            tvSodonhang.setText(item.getOrderNumber());
        }
    }
}
