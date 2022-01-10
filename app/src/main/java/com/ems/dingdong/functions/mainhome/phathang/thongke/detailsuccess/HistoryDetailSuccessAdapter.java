package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class HistoryDetailSuccessAdapter extends RecyclerBaseAdapter {

    public HistoryDetailSuccessAdapter(Context context, List<StatisticDeliveryGeneralResponse> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_statistic_phat_thanh_cong));
    }


    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_service)
        CustomTextView tvService;
        @BindView(R.id.tv_count)
        public CustomTextView tvCount;
        @BindView(R.id.tv_money_cod)
        public CustomTextView tvMoneyCod;
        //        @BindView(R.id.tv_money_c)
//        public CustomTextView tvMoneyC;
        @BindView(R.id.tv_money_cuoc)
        public CustomTextView tvMoneyCuoc;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(final Object model, int position) {
            StatisticDeliveryGeneralResponse item = (StatisticDeliveryGeneralResponse) model;
            tvService.setText(item.getServiceName());
            tvCount.setText(item.getQuantity());

            // tien cod
            tvMoneyCod.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getQuantityCOD()))));


//            // tien c
//            tvMoneyC.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(item.getQuantityC()))));


            // tien ppa

            int tienCuoc = 0;
            if (item.getReceiveCollectFee() != null) {
                tienCuoc += Integer.parseInt(item.getReceiveCollectFee());
            }
            if (item.getFeeCollectLater() > 0) {
                tienCuoc += item.getFeeCollectLater();
            }
            if (item.getFeePA() > 0) {
                tienCuoc += item.getFeePA();
            }
            if (item.getFeePPA() > 0) {
                tienCuoc += item.getFeePPA();
            }
            if (item.getFeeShip() > 0) {
                tienCuoc += item.getFeeShip();
            }


            tvMoneyCuoc.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(tienCuoc)))));


            if (item.getTongtien() > 0)
                tvMoneyCuoc.setText(String.format("%s VNĐ", NumberUtils.formatPriceNumber(Long.parseLong(String.valueOf(item.getTongtien())))));
        }
    }
}
