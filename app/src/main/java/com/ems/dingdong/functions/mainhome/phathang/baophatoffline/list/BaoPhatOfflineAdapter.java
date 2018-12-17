package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import java.util.List;

import butterknife.BindView;

public class BaoPhatOfflineAdapter extends RecyclerBaseAdapter {


    public BaoPhatOfflineAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_offline));
    }

    class HolderView extends BaseViewHolder {

        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_receiverName)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.tv_collect_amount_all)
        CustomTextView tvCollectAmountAll;
        @BindView(R.id.tv_note)
        CustomTextView tvNote;
        @BindView(R.id.tv_ContactPhone)
        CustomTextView tvContactPhone;
        @BindView(R.id.img_clear)
        public ImageView imgClear;
        @BindView(R.id.iv_status)
        public ImageView ivStatus;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvParcelCode.setText(item.getParcelCode());
            if ("2".equals(item.getDeliveryType())) {
                if (!TextUtils.isEmpty(item.getNote())) {
                    tvNote.setText(String.format("Ghi chú phát: %s", item.getNote()));
                    tvNote.setVisibility(View.VISIBLE);
                } else {
                    tvNote.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getRealReceiverName())) {
                    tvReceiverName.setText(item.getRealReceiverName());
                    tvReceiverName.setVisibility(View.VISIBLE);
                } else {
                    tvReceiverName.setText("");
                    tvReceiverName.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getReciverAddress())) {
                    tvReceiverAddress.setText(item.getReciverAddress());
                    tvReceiverAddress.setVisibility(View.VISIBLE);
                } else {
                    tvReceiverAddress.setText("");
                    tvReceiverAddress.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getCollectAmount()) && !TextUtils.isEmpty(item.getReceiveCollectFee())) {
                    tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()) + Long.parseLong(item.getReceiveCollectFee()))));
                } else if (!TextUtils.isEmpty(item.getCollectAmount())) {
                    tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
                } else tvCollectAmountAll.setText("0 đ");

                if (!TextUtils.isEmpty(item.getReceiverPhone())) {
                    String[] phones = item.getReceiverPhone().split(",");
                    if (phones.length > 0) {
                        if (!phones[0].isEmpty()) {
                            // tvContactPhone.setText(phones[0].trim());
                            tvContactPhone.setVisibility(View.VISIBLE);
                        } else {
                            tvContactPhone.setVisibility(View.GONE);
                        }
                    } else {
                        tvContactPhone.setVisibility(View.GONE);
                    }
                } else {
                    tvContactPhone.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getIsCOD())) {
                    if (item.getIsCOD().toUpperCase().equals("Y")) {
                        ivStatus.setVisibility(View.VISIBLE);
                    } else {
                        ivStatus.setVisibility(View.GONE);
                    }
                } else {
                    ivStatus.setVisibility(View.GONE);
                }
            } else {
                if (!TextUtils.isEmpty(item.getReasonName())) {
                    tvReceiverName.setText(item.getReasonName());
                    tvReceiverName.setVisibility(View.VISIBLE);
                } else {
                    tvReceiverName.setText("");
                    tvReceiverName.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getSolutionName())) {
                    tvReceiverAddress.setText(item.getSolutionName());
                    tvReceiverAddress.setVisibility(View.VISIBLE);
                } else {
                    tvReceiverAddress.setText("");
                    tvReceiverAddress.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getIsCOD())) {
                    if (item.getIsCOD().toUpperCase().equals("Y")) {
                        ivStatus.setVisibility(View.VISIBLE);
                    } else {
                        ivStatus.setVisibility(View.GONE);
                    }
                } else {
                    ivStatus.setVisibility(View.GONE);
                }
                tvContactPhone.setVisibility(View.GONE);
            }
        }

    }
}
