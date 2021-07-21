package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneUpdateCallback;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneNumberUpdateDialogIcon extends Dialog {
    private final PhoneCallback mDelegate;
    private DismissDialogCallback dismissCallback;
    private String phone;
    private Context mContext;

    @BindView(R.id.edt_phone)
    CustomEditText edtPhone;

    public PhoneNumberUpdateDialogIcon(Context context, String phone, int type, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone = phone;
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_edit_icon, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        edtPhone.setText(phone);
        dismissCallback = () -> dismiss();

        if (type == 2)
            edtPhone.setEnabled(false);
        else edtPhone.setEnabled(true);
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_update, R.id.tv_close, R.id.tv_update_thuong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_thuong:
                phone = edtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(phone)) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                if (mDelegate != null) {
                    mDelegate.onCallReceiverResponse(phone);
                    dismiss();
                }

                break;
            case R.id.tv_update:
                phone = edtPhone.getText().toString();
                if (!NetworkUtils.isConnected()) {
                    String tam = "," + phone;
                    new ThongbaoGoiDialog(getContext(), phone, new PhoneUpdateCallback() {
                        @Override
                        public void onCall(String phone) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(Constants.HEADER_NUMBER + "," + phone));
                            mContext.startActivity(intent);
                        }
                    }).show();
                } else {
                    if (TextUtils.isEmpty(phone)) {
                        Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                        return;
                    }
                    if (!NumberUtils.checkNumber(phone)) {
                        Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                        return;
                    }
                    mDelegate.onUpdateNumberReceiverResponse(phone, dismissCallback);
                    dismiss();
                }
                break;

            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
