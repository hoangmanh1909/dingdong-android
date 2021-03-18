package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryListProduct;
import com.ems.dingdong.model.DeliveryListRelease;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XacNhanBaoPhatAdapter extends RecyclerView.Adapter<XacNhanBaoPhatAdapter.HolderView> {
    private List<DeliveryPostman> mList;
    private Context mContext;

    public XacNhanBaoPhatAdapter(Context context, List<DeliveryPostman> items) {
        mList = items;
        mContext = context;
    }

    public void setListFilter(List<DeliveryPostman> list) {
        mList = list;
    }

    public List<DeliveryPostman> getListFilter() {
        return mList;
    }

    public List<DeliveryPostman> getItemsSelected() {
        List<DeliveryPostman> commonObjectsSelected = new ArrayList<>();
        List<DeliveryPostman> items = mList;
        for (DeliveryPostman item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    @NonNull
    @Override
    public XacNhanBaoPhatAdapter.HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XacNhanBaoPhatAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanBaoPhatAdapter.HolderView holder, int position) {
        holder.bindView(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pick_status)
        ImageView ivPickStatus;
        @BindView(R.id.rl_item_count_selected)
        RelativeLayout relativeLayout;
        @BindView(R.id.tv_lading)
        CustomTextView tvLading;
        @BindView(R.id.tv_monney)
        CustomTextView tvMonney;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model) {
            DeliveryPostman item = (DeliveryPostman) model;
            if (item.isSelected()) {
                ivPickStatus.setImageResource(R.drawable.ic_check);
                relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background_progressbar));
            } else {
                ivPickStatus.setImageResource(R.drawable.ic_cancel_close);
                relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }

            if (!TextUtils.isEmpty(item.getMaE())) {
                tvLading.setText(item.getMaE());
            } else {
                tvLading.setText("");
            }

            long amount = 0;
            long fee = 0;
            if (item.getAmount() != null) {
                amount = item.getAmount();
            }

            if (item.getTotalFee() != null) {
                fee = item.getTotalFee();
            }
            tvMonney.setText(NumberUtils.formatPriceNumber(amount + fee));
        }
    }
}
