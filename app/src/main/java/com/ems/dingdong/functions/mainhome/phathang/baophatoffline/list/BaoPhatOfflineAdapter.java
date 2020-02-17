package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BaoPhatOfflineAdapter extends RecyclerBaseAdapter {
    List<CommonObject> commonObjects;

    public BaoPhatOfflineAdapter(Context context, List<CommonObject> items) {
        super(context, items);

        commonObjects = items;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_offline));
    }

    public List<CommonObject> getItemsSelected() {
        List<CommonObject> commonObjectsSelected = new ArrayList<>();
        for (CommonObject item : commonObjects) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_delivery_type)
        CustomTextView tvDeliveryType;
        @BindView(R.id.tv_collect_amount)
        CustomTextView tvCollectAmount;
        //        @BindView(R.id.tv_note)
//        CustomTextView tvNote;
        //        @BindView(R.id.tv_ContactPhone)
//        CustomTextView tvContactPhone;
        @BindView(R.id.img_clear)
        public ImageView imgClear;
        //        @BindView(R.id.iv_status)
//        public ImageView ivStatus;
        @BindView(R.id.cb_selected)
        CheckBox cbSelected;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvParcelCode.setText(item.getParcelCode());
            cbSelected.setChecked(item.isSelected());
            cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((CommonObject) model).setSelected(true);
                    } else {
                        ((CommonObject) model).setSelected(false);
                    }
                }
            });

            if ("2".equals(item.getDeliveryType())) {
                tvDeliveryType.setText("Báo phát không thành công");
                tvCollectAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
//                if (!TextUtils.isEmpty(item.getNote())) {
//                    tvNote.setText(String.format("Ghi chú phát: %s", item.getNote()));
//                    tvNote.setVisibility(View.VISIBLE);
//                } else {
//                    tvNote.setVisibility(View.GONE);
//                }
//                if (!TextUtils.isEmpty(item.getRealReceiverName())) {
//                    tvReceiverName.setText(item.getRealReceiverName());
//                    tvReceiverName.setVisibility(View.VISIBLE);
//                } else {
//                    tvReceiverName.setText("");
//                    tvReceiverName.setVisibility(View.GONE);
//                }
//                if (!TextUtils.isEmpty(item.getReciverAddress())) {
//                    tvReceiverAddress.setText(item.getReciverAddress());
//                    tvReceiverAddress.setVisibility(View.VISIBLE);
//                } else {
//                    tvReceiverAddress.setText("");
//                    tvReceiverAddress.setVisibility(View.GONE);
//                }
//                if (!TextUtils.isEmpty(item.getCollectAmount()) && !TextUtils.isEmpty(item.getReceiveCollectFee())) {
//                    tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()) + Long.parseLong(item.getReceiveCollectFee()))));
//                } else if (!TextUtils.isEmpty(item.getCollectAmount())) {
//                    tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
//                } else tvCollectAmountAll.setText("0 đ");
//
//                if (!TextUtils.isEmpty(item.getReceiverPhone())) {
//                    String[] phones = item.getReceiverPhone().split(",");
//                    if (phones.length > 0) {
//                        if (!phones[0].isEmpty()) {
//                            // tvContactPhone.setText(phones[0].trim());
//                            tvContactPhone.setVisibility(View.VISIBLE);
//                        } else {
//                            tvContactPhone.setVisibility(View.GONE);
//                        }
//                    } else {
//                        tvContactPhone.setVisibility(View.GONE);
//                    }
//                } else {
//                    tvContactPhone.setVisibility(View.GONE);
//                }
//                if (!TextUtils.isEmpty(item.getIsCOD())) {
//                    if (item.getIsCOD().toUpperCase().equals("Y")) {
//                        ivStatus.setVisibility(View.VISIBLE);
//                    } else {
//                        ivStatus.setVisibility(View.GONE);
//                    }
//                } else {
//                    ivStatus.setVisibility(View.GONE);
//                }
            } else {
                tvDeliveryType.setText("Báo phát không thành công");
                if (!TextUtils.isEmpty(item.getCollectAmount()))
                    tvCollectAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            }
        }

    }
}
