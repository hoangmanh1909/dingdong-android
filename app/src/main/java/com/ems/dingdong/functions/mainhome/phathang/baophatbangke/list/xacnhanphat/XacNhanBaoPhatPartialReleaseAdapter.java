package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems.dingdong.R;
import com.ems.dingdong.model.DeliveryListRelease;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class XacNhanBaoPhatPartialReleaseAdapter extends RecyclerView.Adapter<XacNhanBaoPhatPartialReleaseAdapter.ViewHolder> {
    private List<DeliveryListRelease> mList;
    private Context mContext;
    private int quantity = 0;

    public XacNhanBaoPhatPartialReleaseAdapter(List<DeliveryListRelease> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partial, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(mList.get(position));
        DeliveryListRelease itemPartialRelease = mList.get(position);
        holder.tvContentItemPartial.setText(itemPartialRelease.getProductName() + " - " + itemPartialRelease.getWeight() + " (g) - Đơn giá: " +
                itemPartialRelease.getPrice());
        holder.tvQuantityItem.setText(itemPartialRelease.getQuantity() + "");

        holder.tvDownQuantityItem.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.tvQuantityItem.getText().toString());
            if (quantity > 0) {
                quantity--;
                itemPartialRelease.setQuantity(quantity);
                holder.tvQuantityItem.setText(itemPartialRelease.getQuantity() + "");
            }
        });
        holder.tvIncreaseQuantityItem.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.tvQuantityItem.getText().toString());
            quantity++;
            itemPartialRelease.setQuantity(quantity);
            holder.tvQuantityItem.setText(itemPartialRelease.getQuantity() + "");
        });
        EventBus.getDefault().postSticky(new CustomQuantityReleases(itemPartialRelease.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content_item_partial)
        TextView tvContentItemPartial;
        @BindView(R.id.tv_quantity_item)
        TextView tvQuantityItem;
        @BindView(R.id.tv_down_quantity_item)
        TextView tvDownQuantityItem;
        @BindView(R.id.tv_increase_quantity_item)
        TextView tvIncreaseQuantityItem;
        @BindView(R.id.tv_background_item)
        TextView tvBackgroundItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        ///
        public void bindView(Object model) {

        }
    }

    public List<DeliveryListRelease> getProductRelease() {
        List<DeliveryListRelease> productRelease = new ArrayList<>();
        List<DeliveryListRelease> items = mList;
        for (DeliveryListRelease item : items) {
            productRelease.add(item);
        }
        return productRelease;
    }

    public List<DeliveryListRelease> updateList(List<DeliveryListRelease> listReleases) {
        mList.clear();
        mList.addAll(listReleases);
        notifyDataSetChanged();
        return listReleases;
    }

    public List<DeliveryListRelease> getListProduct() {
        return mList;
    }

}
