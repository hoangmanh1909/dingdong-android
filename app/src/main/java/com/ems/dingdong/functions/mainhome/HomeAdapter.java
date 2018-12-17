package com.ems.dingdong.functions.mainhome;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.HomeInfo;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class HomeAdapter extends RecyclerBaseAdapter {


    public HomeAdapter(Context context, List<HomeInfo> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_home));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_name)
        CustomBoldTextView tvName;
        @BindView(R.id.tv_info)
        CustomTextView tvInfo;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            HomeInfo homeInfo = (HomeInfo) model;
            img.setImageResource(homeInfo.getResId());
            tvInfo.setText(Html.fromHtml(homeInfo.getInfo()));
            tvName.setText(homeInfo.getName());
        }
    }
}
