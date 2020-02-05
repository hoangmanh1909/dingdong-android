package com.ems.dingdong.functions.mainhome.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;
import com.ems.dingdong.R;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeCollectAdapter extends StickyHeaderGridAdapter {
    private ArrayList<HomeCollectInfo> mList;
    public  HomeCollectAdapter(ArrayList<HomeCollectInfo> items){
        mList = items;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        return  null;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_collect, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {

    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int section, int offset) {

    }

    class MyItemViewHolder extends ItemViewHolder {

        @BindView(R.id.tv_label)
        CustomTextView tv_label;
        @BindView(R.id.tv_count_address)
        CustomTextView tv_count_address;
        @BindView(R.id.tv_count)
        CustomTextView tv_count;
        @BindView(R.id.tv_weight)
        CustomTextView tv_weight;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            HomeCollectInfo homeInfo = (HomeCollectInfo) model;
            if(position == 0)
            {
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count_address.setText(Float.toString(homeInfo.getTotalAddressNotCollect()));
                tv_count.setText(Float.toString(homeInfo.getTotalLadingNotCollect()));
                tv_weight.setText(Float.toString(homeInfo.getTotalWeightNotCollect()));
            }
            else{
                tv_label.setText(homeInfo.getLabelCollect());
                tv_count_address.setText(Float.toString(homeInfo.getTotalAddressCollect()));
                tv_count.setText(Float.toString(homeInfo.getTotalLadingCollect()));
                tv_weight.setText(Float.toString(homeInfo.getTotalWeightCollect()));
            }
        }
    }
}
