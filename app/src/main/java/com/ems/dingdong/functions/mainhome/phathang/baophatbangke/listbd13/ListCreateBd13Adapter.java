package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ems.dingdong.model.Bd13Code;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

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
        @BindView(R.id.tv_contactName)
        CustomTextView tvContactName;
        @BindView(R.id.tv_contact_address)
        CustomTextView tvContactAddress;
        @BindView(R.id.tv_amount)
        CustomTextView tvAmount;
        @BindView(R.id.tv_services)
        CustomTextView tvServices;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Bd13Code model) {
            tvCode.setText(model.getCode());
            tvContactName.setText(String.format("%s", model.getReceiverName()));
            tvContactAddress.setText(model.getAddress());
            if (!TextUtils.isEmpty(model.getCollectAmount())) {
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(model.getCollectAmount()))));
            }
            if (!TextUtils.isEmpty(model.getService())) {
                if (!TextUtils.isEmpty(model.getService().trim())) {
                    tvServices.setText(String.format("%s", model.getService() != null ? model.getService().trim() : ""));
                    tvServices.setVisibility(View.VISIBLE);
                } else {
                    tvServices.setVisibility(View.GONE);
                }
            }
        }
    }
}
