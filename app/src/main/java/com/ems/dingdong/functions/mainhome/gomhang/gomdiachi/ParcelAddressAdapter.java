package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.ParcelCodeInfo;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class ParcelAddressAdapter extends RecyclerBaseAdapter {

    List<ParcelCodeInfo> mList;
    private String text = "";

    public ParcelAddressAdapter(Context context, List<ParcelCodeInfo> items) {
        super(context, items);
        this.mList = items;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_parcel));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_code)
        CustomTextView tvCode;
        @BindView(R.id.tv_sodonhang)
        CustomTextView tvSodonhang;
        @BindView(R.id.cb_selected_parcel)
        public CheckBox cbSelectedParcel;
        @BindView(R.id.img_remove_lading_code)
        ImageView imgRemoveLadingCode;
        @BindView(R.id.layout_parcel_code)
        RelativeLayout layoutParcelCode;
        @BindView(R.id.ll_view)
        View llView;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            ParcelCodeInfo item = (ParcelCodeInfo) model;
            if (item.getTrackingCode().isEmpty() || item.getTrackingCode().equals("")) {
                llView.setVisibility(View.GONE);
                layoutParcelCode.setVisibility(View.GONE);
            } else {
                llView.setVisibility(View.VISIBLE);
                layoutParcelCode.setVisibility(View.VISIBLE);
                tvCode.setText(item.getTrackingCode());
                tvSodonhang.setText(item.getOrderNumber());
            }

        }

        public ParcelCodeInfo getItem(int position) {
            return mList.get(position);
        }
    }

}
