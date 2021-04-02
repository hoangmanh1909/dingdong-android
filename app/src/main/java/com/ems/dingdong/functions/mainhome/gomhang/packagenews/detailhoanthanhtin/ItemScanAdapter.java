package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ScanItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemScanAdapter extends RecyclerBaseAdapter {

    public ItemScanAdapter(Context context, List<ScanItem> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_order_shipment_codes));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_shipment_code)
        TextView tv_shipment_code;
        @BindView(R.id.iv_delete)
        public ImageView iv_delete;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ScanItem item = (ScanItem) model;
            tv_shipment_code.setText((position + 1)+"."+ item.getCode());
        }
    }
}
