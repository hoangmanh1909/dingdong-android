package com.ems.dingdong.functions.mainhome.gomhang.gomnhieu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class ListHoanTatNhieuTinAdapter extends RecyclerBaseAdapter {


    Context mContext;

    public ListHoanTatNhieuTinAdapter(Context context, List<ItemHoanTatNhieuTin> items) {
        super(context, items);
        mContext = context;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_parcel_hoan_tat_nhieu));
    }


    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.img_remove)
        public ImageView imgRemove;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            ItemHoanTatNhieuTin item = (ItemHoanTatNhieuTin) model;
            tvCode.setText(item.getShipmentCode());
            if (item.getStatus() == Constants.GREY) {
                tvCode.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
            } else if (item.getStatus() == Constants.GREEN) {
                tvCode.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
            }
            else {
                tvCode.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }
}
