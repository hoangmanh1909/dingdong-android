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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XacNhanBaoPhatPartialReturnAdapter extends RecyclerView.Adapter<XacNhanBaoPhatPartialReturnAdapter.ViewHolder> {
    private List<DeliveryListRelease> mList;
    private Context mContext;
    private int quantity = 0;
    private String increase = "";
    private String down = "";

    public XacNhanBaoPhatPartialReturnAdapter(List<DeliveryListRelease> mList, Context mContext) {
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
        DeliveryListRelease itemPartialRelease = mList.get(position);
        //quantity = itemBaoPhatMotPhan.getQuantity();
        holder.tvQuantityItem.setText("0");
        itemPartialRelease.setQuantity(0);
        holder.tvContentItemPartial.setText(itemPartialRelease.getProductName() + " - " + itemPartialRelease.getWeight() + " (g) - Đơn giá: " +
                itemPartialRelease.getPrice());

        /*if (itemBaoPhatMotPhan == mList.get(mList.size() - 1)) {
            holder.tvBackgroundItem.setBackgroundResource(R.color.white);
        }*/

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
        EventBus.getDefault().postSticky(new CustomQuantityReturn(itemPartialRelease.getQuantity()));

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

    public List<DeliveryListRelease> getListProduct() {
        return mList;
    }

}
