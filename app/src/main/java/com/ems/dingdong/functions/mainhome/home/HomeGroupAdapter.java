package com.ems.dingdong.functions.mainhome.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;
import com.ems.dingdong.R;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.views.CustomMediumTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeGroupAdapter extends StickyHeaderGridAdapter {

    private ArrayList<GroupInfo> mList;

    public HomeGroupAdapter(ArrayList<GroupInfo> items) {
        mList = items;
    }

    @Override
    public int getSectionCount() {
        return mList.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return mList.get(section).getList().size();
    }

    @Override
    public MyHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_common, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_radius, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        holder.view.setVisibility(View.GONE);

        //  holder.tvHeader.setText(mList.get(section).getNameGroup());
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
        holder.bindView(mList.get(section).getList().get(position), position);
    }

    public class MyHeaderViewHolder extends HeaderViewHolder {

        @BindView(R.id.v_devider)
        View view;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyItemViewHolder extends ItemViewHolder {

        @BindView(R.id.thumbnail)
        ImageView img;
        @BindView(R.id.title)
        CustomMediumTextView tvTitle;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Object model, int position) {
            HomeInfo homeInfo = (HomeInfo) model;
            img.setImageResource(homeInfo.getResId());
            tvTitle.setText(homeInfo.getName());
        }
    }
}
