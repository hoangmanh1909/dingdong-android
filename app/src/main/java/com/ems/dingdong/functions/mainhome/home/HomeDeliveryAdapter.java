package com.ems.dingdong.functions.mainhome.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeDeliveryAdapter extends RecyclerBaseAdapter {
    private ArrayList<HomeCollectInfo> mList;

    public HomeDeliveryAdapter(Context context, ArrayList<HomeCollectInfo> items) {
        super(context, items);
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int itemType) {
        return new HolderView(inflateView(parent, R.layout.item_home_delivery));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_label)
        CustomTextView tv_label;
        @BindView(R.id.tv_count)
        CustomTextView tv_count;
        @BindView(R.id.tv_weight)
        CustomTextView tv_weight;
        @BindView(R.id.tv_COD)
        CustomTextView tv_COD;
        @BindView(R.id.tv_fee)
        CustomTextView tv_fee;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            HomeCollectInfo homeInfo = (HomeCollectInfo) model;
            if (position == 1) {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalQuantityPast()) ? homeInfo.getTotalQuantityPast() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightPast()) ? homeInfo.getTotalWeightPast() : "0");
                tv_COD.setText(!TextUtils.isEmpty(homeInfo.getTotalCODAmountPast()) ? homeInfo.getTotalCODAmountPast() : "0");
                tv_fee.setText(!TextUtils.isEmpty(homeInfo.getTotalFeePast()) ? homeInfo.getTotalFeePast() : "0");
            }
            else if (position == 2){
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalQuantityToday()) ? homeInfo.getTotalQuantityToday() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightToday()) ? homeInfo.getTotalWeightToday() : "0");
                tv_COD.setText(!TextUtils.isEmpty(homeInfo.getTotalCODAmountToday()) ? homeInfo.getTotalCODAmountToday() : "0");
                tv_fee.setText(!TextUtils.isEmpty(homeInfo.getTotalFeeToday()) ? homeInfo.getTotalFeeToday() : "0");
            }else {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalQuantityToday()) ? homeInfo.getTotalQuantityToday() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightToday()) ? homeInfo.getTotalWeightToday() : "0");
                tv_COD.setText(!TextUtils.isEmpty(homeInfo.getTotalCODAmountToday()) ? homeInfo.getTotalCODAmountToday() : "0");
                tv_fee.setText(!TextUtils.isEmpty(homeInfo.getTotalFeeToday()) ? homeInfo.getTotalFeeToday() : "0");
            }
        }
    }
}
