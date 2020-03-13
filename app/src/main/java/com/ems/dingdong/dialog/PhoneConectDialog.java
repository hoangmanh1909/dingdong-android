package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneConectDialog extends Dialog {
    private final PhoneCallback mDelegate;
    private String phone;
    private Context mContext;

    public PhoneConectDialog(Context context, String phone, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone = phone;
        mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_phone_updating, R.id.tv_phone_calling, R.id.tv_phone_messing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_updating:
                new PhoneNumberUpdateDialog(getContext(), phone, mDelegate).show();
                break;

            case R.id.tv_phone_messing:
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
//                    return;
//                }
//                if (!NumberUtils.checkMobileNumber(phone)) {
//                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
//                    return;
//                }
//                mDelegate.onUpdateResponse(phone);
                break;
            case R.id.tv_phone_calling:

                if (TextUtils.isEmpty(phone)) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkMobileNumber(phone)) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                if (mDelegate != null) {
                    mDelegate.onCallResponse(phone);
                    dismiss();
                }
                break;
        }
    }

    public void updateText(String phone) {
        this.phone = phone;
    }
}
