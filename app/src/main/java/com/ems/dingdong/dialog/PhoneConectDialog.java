package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;
import com.ems.dingdong.views.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneConectDialog extends Dialog {
    private final PhoneCallback mDelegate;
    boolean check = false;
    @BindView(R.id.edt_phone)
    CustomEditText edtPhone;
    @BindView(R.id.tv_update_phone)
    CustomTextView tvUpdatePhone;

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

    @OnClick({R.id.tv_update, R.id.tv_close, R.id.tv_update_phone, R.id.tv_call_by_sim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                call(Constants.CALL_SWITCH_BOARD);
                break;

            case R.id.tv_call_by_sim:
                call(Constants.CALL_NORMAL);
                break;
            case R.id.tv_close:
                dismiss();
                break;
            case R.id.tv_update_phone:
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.showToast(edtPhone.getContext(), "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkMobileNumber(edtPhone.getText().toString())) {
                    Toast.showToast(edtPhone.getContext(), "Số điện thoại không hợp lệ.");
                    return;
                }
                mDelegate.onUpdateResponse(edtPhone.getText().toString());
                break;
        }
    }

    public void updateText() {
        tvUpdatePhone.setText("Đã cập nhật");
    }

    private void call(int callType) {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            Toast.showToast(edtPhone.getContext(), "Xin vui lòng nhập SĐT.");
            return;
        }
        if (!NumberUtils.checkMobileNumber(edtPhone.getText().toString())) {
            Toast.showToast(edtPhone.getContext(), "Số điện thoại không hợp lệ.");
            return;
        }
        if (mDelegate != null) {
            mDelegate.onCallResponse(edtPhone.getText().toString(), callType);
            dismiss();
        }
    }
}
