package com.vinatti.dingdong.functions.mainhome.phathang.gachno.searchlist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.GachNo;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class GachNoAdapter extends RecyclerBaseAdapter {


    public GachNoAdapter(Context context, List<GachNo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.gach_no_item));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_receiverName)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.tv_collect_amount_all)
        CustomTextView tvCollectAmountAll;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            GachNo item = (GachNo) model;
            tvParcelCode.setText(item.getLadingCode());
            tvReceiverName.setText(item.getReceiverName());
            tvReceiverAddress.setText(item.getPostmanName());
            if (!TextUtils.isEmpty(item.getCollectAmount()))
                tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
        }

    }
}
