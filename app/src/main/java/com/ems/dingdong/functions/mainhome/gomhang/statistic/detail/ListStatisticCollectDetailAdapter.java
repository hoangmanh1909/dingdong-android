package com.ems.dingdong.functions.mainhome.gomhang.statistic.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.StatisticOrderDetailCollect;
import com.ems.dingdong.views.CustomBoldTextView;

import java.util.List;

import butterknife.BindView;

public class ListStatisticCollectDetailAdapter extends RecyclerBaseAdapter {


    public ListStatisticCollectDetailAdapter(Context context, List<StatisticDetailCollect> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statictis_detail_collect));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_so_tt)
        CustomBoldTextView tvSoTt;
        @BindView(R.id.tv_order_name)
        CustomBoldTextView tvOrderName;
        @BindView(R.id.tv_lading)
        CustomBoldTextView tvLading;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            StatisticDetailCollect item = (StatisticDetailCollect) model;
            tvOrderName.setText(item.getOrderCode());
            tvSoTt.setText(String.format("%s", position + 1));
            StringBuilder ladings = new StringBuilder();
            for (StatisticOrderDetailCollect it : item.getLadings()) {
                ladings.append(it.getLadingCode()).append("\n");
            }
            tvLading.setText(ladings.toString());
        }
    }
}
