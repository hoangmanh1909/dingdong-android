package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ScanItem;
import com.vinatti.dingdong.views.CustomTextView;
import com.vinatti.dingdong.views.Typefaces;

import java.util.List;

import butterknife.BindView;

public class ItemScanAdapter extends RecyclerBaseAdapter {


    public ItemScanAdapter(Context context, List<ScanItem> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, android.R.layout.simple_list_item_1));
    }

    class HolderView extends BaseViewHolder {

        @BindView(android.R.id.text1)
        TextView tvItem;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ScanItem item = (ScanItem) model;
            tvItem.setText(String.format(". %s", item.getCode()));

        }
    }
}
