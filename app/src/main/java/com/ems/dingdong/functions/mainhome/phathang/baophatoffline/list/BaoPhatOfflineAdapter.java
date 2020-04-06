package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BaoPhatOfflineAdapter extends RecyclerBaseAdapter<CommonObject, BaoPhatOfflineAdapter.HolderView> {

    public BaoPhatOfflineAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_offline));
    }

    public List<CommonObject> getItemsSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        for (CommonObject item : mItems) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    class HolderView extends BaseViewHolder<CommonObject> {

        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_delivery_type)
        CustomTextView tvDeliveryType;
        @BindView(R.id.tv_collect_amount)
        CustomTextView tvCollectAmount;
        @BindView(R.id.img_clear)
        public ImageView imgClear;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;
        @BindView(R.id.layout_bp_offline)
        RelativeLayout layoutBpOffline;


        public HolderView(View itemView) {
            super(itemView);
        }

        public CommonObject getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public void bindView(CommonObject model, int position) {
            tvParcelCode.setText(model.getParcelCode());
            if (!TextUtils.isEmpty(model.getDeliveryType())) {
                if ("2".equals(model.getDeliveryType())) {
                    tvDeliveryType.setText(mContext.getResources().getString(R.string.delivery_succesfully));
                } else {
                    tvDeliveryType.setText(mContext.getResources().getString(R.string.delivery_not_succesfully));
                }
            }
            if (!TextUtils.isEmpty(model.getCollectAmount()))
                tvCollectAmount.setText(String.format(mContext.getResources().getString(R.string.amount_of_money)
                        + ": %s đ", NumberUtils.formatPriceNumber(Long.parseLong(model.getCollectAmount()))));
            else
                tvCollectAmount.setText(mContext.getResources().getString(R.string.amount_of_money) + ": 0 đ");

            cbSelected.setOnCheckedChangeListener((v1, v2) -> {
                if (v2) {
                    layoutBpOffline.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    layoutBpOffline.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });
            cbSelected.setChecked(model.isSelected());
        }

    }
}
