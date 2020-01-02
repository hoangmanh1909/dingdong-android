package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.StatisticDetailCollect;
import com.ems.dingdong.model.StatisticOrderDetailCollect;
import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;

import java.util.List;

import butterknife.BindView;

public class ListDeliverySuccessCollectDetailAdapter extends RecyclerBaseAdapter {


    public ListDeliverySuccessCollectDetailAdapter(Context context, List<StatisticDeliveryDetailResponse> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statictis_delivery_success_detail_collect));
    }

    public class HolderView extends BaseViewHolder {
        @BindView(R.id.tv_so_tt)
        CustomBoldTextView tvSoTt;
        @BindView(R.id.tv_LadingCode)
        CustomBoldTextView tvLadingCode;
        @BindView(R.id.tv_Amount)
        CustomBoldTextView tvAmount;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            StatisticDeliveryDetailResponse item = (StatisticDeliveryDetailResponse) model;
            tvLadingCode.setText(item.getLadingCode());
            tvSoTt.setText(String.format("%s", position + 1));
            tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getAmount()))));
        }
    }
}
