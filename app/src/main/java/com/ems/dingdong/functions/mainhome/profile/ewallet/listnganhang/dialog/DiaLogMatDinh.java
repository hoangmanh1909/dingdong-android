package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank.DialogOTP;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaLogMatDinh extends Dialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sub_title)
    CustomTextView tvSubTitle;

    private DiaLogMatDinh.OnPaymentCallback callback;
    private Context context;
    private long lastClickTime = 0;

    public DiaLogMatDinh(Context context, String message, String title, DiaLogMatDinh.OnPaymentCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_matdinh, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.callback = callback;
        this.context = context;
        tvSubTitle.setText(message);
        tvTitle.setText(title);
    }


    @OnClick({R.id.tv_huybo, R.id.tv_xac_nhan, R.id.ic_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel:
            case R.id.tv_huybo:
                dismiss();
                break;
            case R.id.tv_xac_nhan:
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                callback.onPaymentClick("1");
                dismiss();
                break;
        }
    }

    public interface OnPaymentCallback {
        void onPaymentClick(String otp);
    }
}
