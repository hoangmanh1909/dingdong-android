package com.ems.dingdong.functions.mainhome.gomhang.statistic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.views.CustomBoldTextView;

import java.util.List;

import butterknife.BindView;

public class ListStatisticCollectAdapter extends RecyclerBaseAdapter {


    public ListStatisticCollectAdapter(Context context, List<StatisticCollect> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statictis_collect));
    }

    public class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_status_name)
        CustomBoldTextView tvStatusName;
        @BindView(R.id.tv_count)
        CustomBoldTextView tvCount;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            StatisticCollect item = (StatisticCollect) model;
            tvStatusName.setText(item.getStatusName());
            tvCount.setText(item.getCount());

        }
    }
}
