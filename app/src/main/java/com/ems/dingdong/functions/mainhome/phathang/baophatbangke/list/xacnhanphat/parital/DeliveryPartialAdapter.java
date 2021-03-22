package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ProductModel;
import com.ems.dingdong.utiles.NumberUtils;

import java.util.List;

import butterknife.BindView;

public class DeliveryPartialAdapter extends RecyclerBaseAdapter {

    Activity activity;
    String mode;

    public DeliveryPartialAdapter(Context context, List items, String mode) {
        super(context, items);
        activity = (Activity) context;
        this.mode = mode;
    }

    @Override
    public DeliveryPartialAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeliveryPartialAdapter.HolderView(inflateView(parent, R.layout.item_delivery_partial));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((DeliveryPartialAdapter.HolderView) holder, position);
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_quantity)
        public TextView tv_quantity;
        @BindView(R.id.iv_decrease)
        public ImageView iv_decrease;
        @BindView(R.id.iv_increase)
        public ImageView iv_increase;
        @BindView(R.id.iv_delete)
        public ImageView iv_delete;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            ProductModel item = (ProductModel) model;

            int _pos = position + 1;
            tv_quantity.setText(item.getQuantity() + "");
            if (mode.equals("ADD")) {
                iv_delete.setVisibility(View.GONE);
                tv_content.setText(_pos + "." + item.getProductName() + " - " + item.getWeight() + " (g) - Đơn giá: " + NumberUtils.formatPriceNumber(item.getPrice()));
            } else if (mode.equals("REFUND")) {
                tv_content.setText(_pos + "." + item.getProductName() + " - " + item.getWeight() + " (g) - Đơn giá: " + NumberUtils.formatPriceNumber(item.getPrice()));
                iv_decrease.setVisibility(View.GONE);
                iv_increase.setVisibility(View.GONE);
                iv_delete.setVisibility(View.GONE);
            } else {
                tv_content.setText(_pos + "." + item.getProductName() + " - " + item.getWeight() + " (g) - Đơn giá: " + NumberUtils.formatPriceNumber(item.getPrice()));
                iv_decrease.setVisibility(View.GONE);
                iv_increase.setVisibility(View.GONE);
                iv_delete.setVisibility(View.VISIBLE);
            }
        }
    }
}
