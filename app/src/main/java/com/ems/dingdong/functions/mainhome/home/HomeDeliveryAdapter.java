package com.ems.dingdong.functions.mainhome.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiActivity;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
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

        void setItemClickListener1(int newDeliveryType, int notDeliveryType) {
            Intent intent1 = new Intent(mContext, XacNhanDiaChiActivity.class);
            tv_column_1.setOnClickListener(v -> {
                intent1.putExtra(Constants.TYPE_GOM_HANG, 1);
                mContext.startActivity(intent1);
            });
            Intent intent2 = new Intent(mContext, XacNhanDiaChiActivity.class);
            tv_column_2.setOnClickListener(v -> {
                intent2.putExtra(Constants.TYPE_GOM_HANG, 4);
                mContext.startActivity(intent2);
            });

        }

        void setItemClickListener2(int newDeliveryType, int notDeliveryType) {
            Intent intent1 = new Intent(mContext, ListCommonActivity.class);
            tv_column_1.setOnClickListener(v -> {
                intent1.putExtra(Constants.TYPE_GOM_HANG, 1);
                mContext.startActivity(intent1);
            });
            Intent intent2 = new Intent(mContext, ListCommonActivity.class);
            tv_column_2.setOnClickListener(v -> {
                intent2.putExtra(Constants.TYPE_GOM_HANG, 2);
                mContext.startActivity(intent2);
            });
        }


        public void bindView(Object model, int position) {
            HomeCollectInfo homeInfo = (HomeCollectInfo) model;
            Intent intent = new Intent(mContext, ListBaoPhatBangKeActivity.class);
            if (homeInfo.getType() == 0) {
                if (position == 0) {
                    tv_label.setText("");
                    tv_column_1.setText(homeInfo.getTotalAddressCollect());
                    tv_column_2.setText(homeInfo.getTotalAddressNotCollect());
                    tv_column_1.setTypeface(null, Typeface.BOLD);
                    tv_column_2.setTypeface(null, Typeface.BOLD);

                } else if (position == 1) {
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(homeInfo.getTotalAddressCollect());
                    tv_column_2.setText(homeInfo.getTotalAddressNotCollect());
                    setItemClickListener1(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                } else if (position == 2) {
                    setItemClickListener2(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalLadingCollect())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalLadingNotCollect())));
                } else {
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalWeightCollect())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalWeightNotCollect())));
                }
            } else if (homeInfo.getType() == 1) {
                if (position == 0) {
                    tv_label.setText("");
                    tv_column_1.setText(homeInfo.getTotalQuantityToday());
                    tv_column_2.setText(homeInfo.getTotalQuantityPast());
                    tv_column_1.setTypeface(null, Typeface.BOLD);
                    tv_column_2.setTypeface(null, Typeface.BOLD);
                } else if (position == 1) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(homeInfo.getTotalQuantityToday());
                    tv_column_2.setText(homeInfo.getTotalQuantityPast());
                } else if (position == 2) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_NORMAL_NEW, Constants.DELIVERY_LIST_TYPE_NORMAL);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeeToday())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeePast())));
                }
            } else if (homeInfo.getType() == 2) {
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
                } else if (position == 3) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_COD_NEW, Constants.DELIVERY_LIST_TYPE_COD);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeeTodayCOD())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeePastCOD())));
                }
            } else {
                if (position == 0) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_PA_NEW, Constants.DELIVERY_LIST_TYPE_PA);
                    tv_label.setText("");
                    tv_column_1.setText(homeInfo.getTotalQuantityToday());
                    tv_column_2.setText(homeInfo.getTotalQuantityPast());
                    tv_column_1.setTypeface(null, Typeface.BOLD);
                    tv_column_2.setTypeface(null, Typeface.BOLD);
                } else if (position == 1) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_PA_NEW, Constants.DELIVERY_LIST_TYPE_PA);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalQuantityTodayPA())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalQuantityPastPA())));
                } else if (position == 2) {
                    setItemClickListener(Constants.DELIVERY_LIST_TYPE_PA_NEW, Constants.DELIVERY_LIST_TYPE_PA);
                    tv_label.setText(homeInfo.getLabelCollect());
                    tv_column_1.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeeTodayPA())));
                    tv_column_2.setText(String.format("%s", NumberUtils.formatPriceNumber(homeInfo.getTotalFeePastPA())));
                }
            }
        }
    }
}
