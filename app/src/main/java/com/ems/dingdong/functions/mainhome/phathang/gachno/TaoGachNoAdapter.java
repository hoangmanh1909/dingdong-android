package com.ems.dingdong.functions.mainhome.phathang.gachno;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class TaoGachNoAdapter extends RecyclerBaseAdapter {


    public TaoGachNoAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_thanh_cong));
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
        @BindView(R.id.img_ContactPhone)
        ImageView imgContactPhone;
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
            tvNote.setText("Ghi chú phát: ");
            tvReceiverName.setText(item.getReciverName());
            tvReceiverAddress.setText(item.getReciverAddress());
            if (!TextUtils.isEmpty(item.getCollectAmount()) && !TextUtils.isEmpty(item.getReceiveCollectFee())) {
                tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()) + Long.parseLong(item.getReceiveCollectFee()))));

            } else {
                if (!TextUtils.isEmpty(item.getCollectAmount()))
                    tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            }
            String[] phones = item.getReceiverPhone().split(",");
            if (phones.length > 0) {
                if (!phones[0].isEmpty()) {
                    // imgContactPhone.setText(phones[0].trim());
                    imgContactPhone.setVisibility(View.VISIBLE);
                } else {
                    imgContactPhone.setVisibility(View.GONE);
                }
            } else {
                imgContactPhone.setVisibility(View.GONE);
            }
            if (item.getIsCOD().toUpperCase().equals("Y")) {
                ivStatus.setVisibility(View.VISIBLE);
            } else {
                ivStatus.setVisibility(View.GONE);
            }
        }

    }
}
