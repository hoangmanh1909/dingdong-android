package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmDialog extends Dialog {

    @BindView(R.id.confirm_content)
    CustomTextView content;
    @BindView(R.id.total_record)
    CustomBoldTextView totalRecord;
    @BindView(R.id.total_amount)
    CustomBoldTextView totalAmount;


    private OnCancelClickListener cancelClickListener;
    private OnOkClickListener okClickListener;

    public ConfirmDialog(Context context, int quantity, long amount) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_confirm, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        totalRecord.setText(String.valueOf(quantity));
        totalAmount.setText(String.format("%s đ", NumberUtils.formatPriceNumber(amount)));
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_cancel_dialog, R.id.tv_ok_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_dialog:
                cancelClickListener.onClick(this);
                break;

            case R.id.tv_ok_dialog:
                okClickListener.onClick(this);
                break;
        }
    }

    public ConfirmDialog setOnCancelListener(OnCancelClickListener listener) {
        this.cancelClickListener = listener;
        return this;
    }

    public ConfirmDialog setOnOkListener(OnOkClickListener listener) {
        this.okClickListener = listener;
        return this;
    }

    public ConfirmDialog setWarning(String warning) {
        content.setText(warning);
        return this;
    }

    public interface OnCancelClickListener {
        void onClick(ConfirmDialog confirmDialog);
    }

    public interface OnOkClickListener {
        void onClick(ConfirmDialog confirmDialog);
    }
}
