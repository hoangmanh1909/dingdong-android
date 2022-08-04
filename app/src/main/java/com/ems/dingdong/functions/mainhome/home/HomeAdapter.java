package com.ems.dingdong.functions.mainhome.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;
import com.ems.dingdong.R;
import com.ems.dingdong.model.GroupInfo;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.views.CustomMediumTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HolderView> {

    List<HomeInfo> mListFilter;
    List<HomeInfo> mList;
    Context mContext;

    public HomeAdapter(Context context, List<HomeInfo> items) {
        mContext = context;
        mList = items;
        mListFilter = items;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    @Override
    public HomeAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_radius, parent, false));
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView img;
        @BindView(R.id.title)
        CustomMediumTextView tvTitle;

        public HolderView(View itemView) {
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
