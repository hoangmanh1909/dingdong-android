package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.ems.dingdong.R;
import com.ems.dingdong.views.CustomTextView;

import java.util.List;

import butterknife.BindView;

public class PaymentAdapter extends RecyclerBaseAdapter<String, PaymentAdapter.ViewHolder> {

    public PaymentAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflateView(parent, R.layout.item_payment));
    }

    public class ViewHolder extends BaseViewHolder<String> {

        @BindView(R.id.tv_lading)
        CustomTextView tvLadingCode;
        @BindView(R.id.tv_cod)
        CustomTextView tvCod;
        @BindView(R.id.tv_fee)
        CustomTextView tvFee;
        @BindView(R.id.cb_selected)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(String model, int position) {
            tvLadingCode.setText("PA3210910310VN");
            tvCod.setText("COD: 20.000đ");
            tvFee.setText("Cước: 10.000đ");
            itemView.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
        }
    }
}
