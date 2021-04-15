package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneNumberUpdateDialog extends Dialog {
    private final PhoneCallback mDelegate;
    private DismissDialogCallback dismissCallback;
    private String phone;
    private Context mContext;

    @BindView(R.id.edt_phone)
    CustomEditText edtPhone;

    public PhoneNumberUpdateDialog(Context context, String phone,int type, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone = phone;
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_edit, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        edtPhone.setText(phone);
        dismissCallback = () -> dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_update, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                if (TextUtils.isEmpty(edtPhone.getText())) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(edtPhone.getText().toString())) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                mDelegate.onUpdateNumberReceiverResponse(edtPhone.getText().toString(), dismissCallback);
                dismiss();
                break;

            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
