package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.form.FormItemEditText;

import java.util.List;

import butterknife.BindView;

public class CuocAdapter extends RecyclerBaseAdapter {

    Activity activity;

    public CuocAdapter(Context context, List items) {
        super(context, items);
        activity = (Activity) context;
    }

    @Override
    public CuocAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CuocAdapter.HolderView(inflateView(parent, R.layout.item_cuoc));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((CuocAdapter.HolderView) holder, position);
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_monney)
        CustomTextView tv_monney;
        @BindView(R.id.tv_lading)
        CustomTextView tv_lading;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            ModeFee item = (ModeFee) model;

            tv_lading.setText(item.getTenloaiFee());

            tv_monney.setText(String.format(" %s đ", NumberUtils.formatPriceNumber(item.getFee())));

        }
    }
}
