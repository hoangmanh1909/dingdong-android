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

public class HomeCollectAdapter extends RecyclerBaseAdapter {
    private ArrayList<HomeCollectInfo> mList;

    public HomeCollectAdapter(Context context, ArrayList<HomeCollectInfo> items) {
        super(context, items);
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int itemType) {
        return new HolderView(inflateView(parent, R.layout.item_home_collect));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_label)
        CustomTextView tv_label;
        @BindView(R.id.tv_count_address)
        CustomTextView tv_count_address;
        @BindView(R.id.tv_count)
        CustomTextView tv_count;
        @BindView(R.id.tv_weight)
        CustomTextView tv_weight;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            HomeCollectInfo homeInfo = (HomeCollectInfo) model;
            if (position == 1) {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count_address.setText(!TextUtils.isEmpty(homeInfo.getTotalAddressNotCollect()) ? homeInfo.getTotalAddressNotCollect() : "0");
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalLadingNotCollect()) ? homeInfo.getTotalLadingNotCollect() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightNotCollect()) ? homeInfo.getTotalWeightNotCollect() : "0");
            }
            else if (position == 2) {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count_address.setText(!TextUtils.isEmpty(homeInfo.getTotalAddressCollect()) ? homeInfo.getTotalAddressCollect() : "0");
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalLadingCollect()) ? homeInfo.getTotalLadingCollect() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightCollect()) ? homeInfo.getTotalWeightCollect() : "0");
            }else {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count_address.setText(!TextUtils.isEmpty(homeInfo.getTotalAddressNotCollect()) ? homeInfo.getTotalAddressNotCollect() : "0");
                tv_count.setText(!TextUtils.isEmpty(homeInfo.getTotalLadingNotCollect()) ? homeInfo.getTotalLadingNotCollect() : "0");
                tv_weight.setText(!TextUtils.isEmpty(homeInfo.getTotalWeightNotCollect()) ? homeInfo.getTotalWeightNotCollect() : "0");
            }
        }
    }
}
