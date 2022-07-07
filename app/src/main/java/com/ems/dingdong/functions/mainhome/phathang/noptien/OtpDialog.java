package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.RelativeLayout;

import com.chaos.view.PinView;
import com.ems.dingdong.R;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.utiles.KeyboardUtil;
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

    @BindView(R.id.rootView)
    RelativeLayout rootView;

    private OnPaymentCallback callback;
    private Context context;
    private long lastClickTime = 0;
    int mType = 0;
    SmartBankLink item;

    public OtpDialog(Context context, int type, SmartBankLink item, OnPaymentCallback callback, String message) {
        super(context, R.style.DialogStyle);
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

    @OnClick({R.id.ic_cancel, R.id.tv_pay,R.id.rootView})
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
            case R.id.rootView:{
                KeyboardUtil.hide(rootView);
                break;
            }
        }
    }

    interface OnPaymentCallback {
        void onPaymentClick(String otp);
    }
}
