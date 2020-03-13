package com.ems.dingdong.functions.mainhome.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeDeliveryAdapter extends RecyclerBaseAdapter {
    private ArrayList<HomeCollectInfo> mList;

    public HomeDeliveryAdapter(Context context, ArrayList<HomeCollectInfo> items) {
        super(context, items);
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int itemType) {
        return new HolderView(inflateView(parent, R.layout.item_home_delivery));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_label)
        CustomTextView tv_label;
        @BindView(R.id.tv_column_1)
        CustomTextView tv_column_1;
        @BindView(R.id.tv_column_2)
        CustomTextView tv_column_2;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setItemClickListener(int newDeliveryType, int notDeliveryType) {
            Intent intent = new Intent(mContext, ListBaoPhatBangKeActivity.class);
            tv_column_1.setOnClickListener(v -> {
                intent.putExtra(Constants.DELIVERY_LIST_TYPE, newDeliveryType);
                mContext.startActivity(intent);
            });
            tv_column_2.setOnClickListener(v -> {
                intent.putExtra(Constants.DELIVERY_LIST_TYPE, notDeliveryType);
                mContext.startActivity(intent);
            });
        }

        public void bindView(Object model, int position) {
            HomeCollectInfo homeInfo = (HomeCollectInfo) model;
            Intent intent = new Intent(mContext, ListBaoPhatBangKeActivity.class);
            if(homeInfo.getType() == 1) {
                if (position == 0) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                    tv_label.setText("");
                    tv_column_1.setText(homeInfo.getTotalQuantityToday());
                    tv_column_2.setText(homeInfo.getTotalQuantityPast());
                    tv_column_1.setTypeface(null, Typeface.BOLD);
                    tv_column_2.setTypeface(null, Typeface.BOLD);
                } else if (position == 1) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(Integer.parseInt(homeInfo.getTotalQuantityToday()))));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(Integer.parseInt(homeInfo.getTotalQuantityPast()))));
                } else if (position == 2) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW_FEE, Constants.DELIVERY_LIST_TYPE_NORMAL_FEE);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeeToday())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeePast())));
                }
            }
            else{
                if (position == 0) {
                    tv_label.setText("");
                    tv_column_1.setText(homeInfo.getTotalQuantityToday());
                    tv_column_2.setText(homeInfo.getTotalQuantityPast());
                    tv_column_1.setTypeface(null, Typeface.BOLD);
                    tv_column_2.setTypeface(null, Typeface.BOLD);
                } else if (position == 1) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_COD_NEW, Constants.DELIVERY_LIST_TYPE_COD);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalQuantityTodayCOD())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalQuantityPastCOD())));
                } else if (position == 2) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_COD_NEW, Constants.DELIVERY_LIST_TYPE_COD);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalCODAmountTodayCOD())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalCODAmountPastCOD())));
                }
                else if (position == 3) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_COD_NEW_FEE, Constants.DELIVERY_LIST_TYPE_COD_FEE);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeeTodayCOD())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeePastCOD())));
                }
            }
        }
    }
}
