package com.vinatti.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.callback.PhoneCallback;
import com.vinatti.dingdong.callback.ReasonCallback;
import com.vinatti.dingdong.utiles.NumberUtils;
import com.vinatti.dingdong.utiles.Toast;
import com.vinatti.dingdong.views.CustomBoldTextView;
import com.vinatti.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneConectDialog extends Dialog {
    private final PhoneCallback mDelegate;
    boolean check = false;
    @BindView(R.id.edt_phone)
    CustomEditText edtPhone;

    public PhoneConectDialog(Context context, String phone, PhoneCallback reasonCallback) {

        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        edtPhone.setText(phone);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_update, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.showToast(edtPhone.getContext(), "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkMobileNumber(edtPhone.getText().toString())) {
                    Toast.showToast(edtPhone.getContext(), "Số điện thoại không hợp lệ.");
                    return;
                }
                if (mDelegate != null) {
                    mDelegate.onCallResponse(edtPhone.getText().toString());
                    dismiss();
                }

                break;
            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
