package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaoPhatThanhCongAdapter extends RecyclerBaseAdapter {


    public BaoPhatThanhCongAdapter(Context context, List<CommonObject> items) {
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
        @BindView(R.id.img_clear)
        public ImageView imgClear;

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
            if (!TextUtils.isEmpty(item.getCollectAmount()) && TextUtils.isEmpty(item.getReceiveCollectFee())) {
                tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()))));
            } else {
                tvCollectAmountAll.setText(String.format("%s đ", NumberUtils.formatPriceNumber(Long.parseLong(item.getCollectAmount()) + Long.parseLong(item.getReceiveCollectFee()))));
            }
        }
    }

}
