package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import com.chaos.view.PinView;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.utiles.DialogUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OtpEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpDialog extends Dialog {

    @BindView(R.id.firstPinView)
    PinView pinView;

    @BindView(R.id.tv_sub_title)
    CustomTextView tvMessage;

    private OnPaymentCallback callback;
    private Context context;
    private long lastClickTime = 0;
    int mType = 0;
    SmartBankLink item;

    public OtpDialog(Context context, int type, SmartBankLink item, OnPaymentCallback callback, String message) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_otp, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        this.context = context;
        this.item = item;
        mType = type;
        tvMessage.setText(message);
        pinView.setItemCount(item.getOTPLength());

    }

    @OnClick({R.id.ic_cancel, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.tv_pay:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                if (pinView.getText().toString().isEmpty()) {
                    Toast.showToast(getContext(), "OTP không được để trống.");
                    return;
                }
                if (pinView.getText().toString().length() != item.getOTPLength()) {
                    Toast.showToast(getContext(), "Bạn đã nhập sai OTP! Vui lòng nhập đúng độ dài OTP!.");
                    return;
                }
                callback.onPaymentClick(pinView.getText().toString());
                break;
        }
    }

    interface OnPaymentCallback {
        void onPaymentClick(String otp);
    }
}
