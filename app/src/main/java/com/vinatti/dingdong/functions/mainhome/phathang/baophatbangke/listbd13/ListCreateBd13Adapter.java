package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.Bd13Code;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListCreateBd13Adapter extends RecyclerView.Adapter<ListCreateBd13Adapter.HolderView> {


    private List<Bd13Code> mList;

    public ListCreateBd13Adapter(Context context, List<Bd13Code> items) {

        mList = items;

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_create_bd13, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }


    class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_code)
        CustomBoldTextView tvCode;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Bd13Code model) {
            tvCode.setText(model.getCode());
        }
    }
}
