package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.R;

import java.util.List;

import butterknife.BindView;

public class BaoPhatAdapter extends RecyclerBaseAdapter {


    public BaoPhatAdapter(Context context, List<CommonObject> items) {
        super(context, items);
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderView(inflateView(parent, R.layout.item_bao_phat_common));
    }

    class HolderView extends BaseViewHolder {
        @BindView(R.id.tv_parcelCode)
        CustomBoldTextView tvParcelCode;
        @BindView(R.id.tv_sender_name)
        CustomBoldTextView tvSenderName;
        @BindView(R.id.tv_sender_address)
        CustomTextView tvSenderAddress;

        public HolderView(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            CommonObject item = (CommonObject) model;
            tvParcelCode.setText(item.getParcelCode());
            tvSenderName.setText(item.getSenderName());
            tvSenderAddress.setText(item.getSenderAddress());
        }
    }

}
