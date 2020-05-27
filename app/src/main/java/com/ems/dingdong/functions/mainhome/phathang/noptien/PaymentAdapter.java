package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PaymentAdapter extends RecyclerBaseAdapter<EWalletDataResponse, PaymentAdapter.ViewHolder> {

    public PaymentAdapter(Context context, List<EWalletDataResponse> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.item_payment));
    }

    public List<EWalletDataResponse> getItemsSelected() {
        List<EWalletDataResponse> commonObjectsSelected = new ArrayList<>();
        List<EWalletDataResponse> items = mItems;
        for (EWalletDataResponse item : items) {
            if (item.isSelected()) {
                commonObjectsSelected.add(item);
            }
        }
        return commonObjectsSelected;
    }

    public class ViewHolder extends BaseViewHolder<EWalletDataResponse> {

        @BindView(R.id.tv_code)
        CustomBoldTextView tvLadingCode;
        @BindView(R.id.tv_index)
        CustomBoldTextView index;
        @BindView(R.id.tv_cod_amount)
        CustomTextView tvCod;
        @BindView(R.id.tv_fee)
        CustomTextView tvFee;

        @BindView(R.id.tv_receiver_name)
        CustomTextView tvReceiverName;
        @BindView(R.id.tv_receiver_address)
        CustomTextView tvReceiverAddress;
        @BindView(R.id.cb_selected)
        CheckBox checkBox;
        @BindView(R.id.ll_item_payment)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(EWalletDataResponse model, int position) {
            index.setText((position + 1) + " - ");
            if (!TextUtils.isEmpty(model.getLadingCode()))
                tvLadingCode.setText(model.getLadingCode());
            else
                tvLadingCode.setText("");

            if (model.getCodAmount() != null)
                tvCod.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), NumberUtils.formatPriceNumber(model.getCodAmount())));
            else
                tvCod.setText(String.format("%s: %s", mContext.getString(R.string.cod_amount), "0"));

            if (model.getFee() != null)
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), NumberUtils.formatPriceNumber(model.getFee())));
            else
                tvFee.setText(String.format("%s: %s", mContext.getString(R.string.fee_money), "0"));

            if (!TextUtils.isEmpty(model.getReceiverName()))
                tvReceiverName.setText(String.format("%s: %s", mContext.getString(R.string.receiver_name), model.getReceiverName()));
            else
                tvReceiverName.setText("");

            if (!TextUtils.isEmpty(model.getReceiverAddress()))
                tvReceiverAddress.setText(String.format("%s: %s", mContext.getString(R.string.address_receiver_name), model.getReceiverAddress()));
            else
                tvReceiverAddress.setText("");
            itemView.setOnClickListener(v -> {
                model.setSelected(!model.isSelected());
                checkBox.setChecked(model.isSelected());
            });
            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_background_bd13));
                } else {
                    linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }
            });
        }
    }
}
