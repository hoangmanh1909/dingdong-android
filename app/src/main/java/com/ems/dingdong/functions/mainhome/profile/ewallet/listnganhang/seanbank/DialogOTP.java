package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

import com.ems.dingdong.R;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;
import com.ems.dingdong.views.OtpEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogOTP extends Dialog {

    @BindView(R.id.et_otp)
    EditText optEditText;
    @BindView(R.id.tv_sub_title)
    CustomTextView tvMessage;

    private OnPaymentCallback callback;
    private Context context;
    private long lastClickTime = 0;

    public DialogOTP(Context context, String message, OnPaymentCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_otp_lienket, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        this.context = context;
        tvMessage.setText(message);
    }

    @OnClick({R.id.ic_cancel, R.id.tv_pay, R.id.tv_guilai_otp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
                dismiss();
                break;
            case R.id.tv_guilai_otp:
                optEditText.setText("");
                callback.onCallOTP();
                break;
            case R.id.tv_pay:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                if (optEditText.getText().toString().trim().isEmpty()) {
                    Toast.showToast(context, "Chưa nhập OTP hoặc OTP chưa đúng định dạng");
                    return;
                }

                if (optEditText.getText().toString().length() < 6) {
                    Toast.showToast(context, "OTP phải từ 6 ký tự trở lên");
                    optEditText.setText("");
                    return;
                }

                callback.onPaymentClick(optEditText.getText().toString(), 0);
                break;
        }
    }

    public interface OnPaymentCallback {
        void onPaymentClick(String otp, int type);

        void onCallOTP();
    }
}
