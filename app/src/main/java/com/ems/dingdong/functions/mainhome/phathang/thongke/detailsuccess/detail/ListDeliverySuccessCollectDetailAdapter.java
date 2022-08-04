package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        @BindView(R.id.tv_feename)
        TextView tvFeename;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            StatisticDeliveryDetailResponse item = (StatisticDeliveryDetailResponse) model;
            tvLadingCode.setText(item.getLadingCode());
            if (position == mItems.size() - 1) {
                tvSoTt.setText("Tổng");
            } else {
                tvSoTt.setText(String.format("%s", position + 1));
            }
            tvFeename.setVisibility(View.GONE);
            int tienCuoc = 0;
            if (item.getFeePPA() > 0) {
                tienCuoc += item.getFeePPA();
            }
            if (item.getFeeCollectLater() > 0) {
                tienCuoc += item.getFeeCollectLater();
            }
            if (item.getFeePA() > 0) {
                tienCuoc += item.getFeePA();
            }
            if (item.getFeeShip() > 0) {
                tienCuoc += item.getFeeShip();
            }
            if (item.getReceiveCollectFee() != null) {
                tienCuoc += Integer.parseInt(item.getReceiveCollectFee());
            }
            if (item.getAmount() != null) {
                tienCuoc += Integer.parseInt(item.getAmount());
            }
            tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(tienCuoc)))));

            if (item.getTongtien() > 0)
                tvAmount.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(item.getTongtien())))));
        }
    }
}
