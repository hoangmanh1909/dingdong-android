package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ems.dingdong.R;
import com.ems.dingdong.callback.DismissDialogCallback;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.views.CustomEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneNumberUpdateSenderDialog extends Dialog {
    private final PhoneCallback mDelegate;
    private DismissDialogCallback dismissCallback;
    private Context mContext;
    private String phoneSenders;

    @BindView(R.id.edt_phone_sender)
    CustomEditText edtPhoneSender;

    public PhoneNumberUpdateSenderDialog(Context context, String phoneSender,int typeCall, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phoneSenders = phoneSender;
        this.mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_edit_phone_sender, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        edtPhoneSender.setText(phoneSender);
        dismissCallback = () -> dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_update_sender, R.id.tv_close_sender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_sender:
                if (TextUtils.isEmpty(edtPhoneSender.getText())) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(edtPhoneSender.getText().toString())) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                mDelegate.onUpdateNumberSenderResponse(edtPhoneSender.getText().toString(), dismissCallback);
                dismiss();
                break;

            case R.id.tv_close_sender:
                dismiss();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventPhoneSender(CustomNumberSender customNumberSender){
        phoneSenders = customNumberSender.getMessage();
    }
}
