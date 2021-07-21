package com.ems.dingdong.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneUpdateCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.sip.cmc.SipCmc;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneConectDialogIcon extends Dialog {
    private final PhoneCallback mDelegate;
    private String phone;
    private Context mContext;
    private String numberSender;

    private PortSipService portSipService;

    @SuppressLint("SetTextI18n")
    public PhoneConectDialogIcon(Context context, String phone, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone = phone;
        mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_icon, null);
        setContentView(view);
        ButterKnife.bind(this, view);
//        PortSipService.PortSipBinder binder = (PortSipService.PortSipBinder) service;
//        portSipService = binder.getService();
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
//            Logger.d(TAG, "onServiceConnected");
//            PortSipService.PortSipBinder binder = (PortSipService.PortSipBinder) service;
//            portSipService = binder.getService();
//            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_phone_updating, R.id.tv_phone_calling, R.id.tv_call_CSKH})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_updating:
                new PhoneNumberUpdateDialogIcon(getContext(), phone, 1, mDelegate).show();
                break;
            case R.id.tv_phone_calling:
                if (!NetworkUtils.isConnected()) {
                    String tam = "," + phone;
                    new ThongbaoGoiDialog(getContext(), phone, new PhoneUpdateCallback() {
                        @Override
                        public void onCall(String phone) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(Constants.HEADER_NUMBER + tam));
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
                    SipCmc.callTo(phone);
                    Intent intent1 = new Intent(getContext(), IncomingCallActivity.class);
                    intent1.putExtra(Constants.CALL_TYPE, 1);
                    intent1.putExtra(Constants.KEY_CALLEE_NUMBER, phone);
                    getContext().startActivity(intent1);
                    dismiss();
                }
                break;
            case R.id.tv_call_CSKH:
                if (mDelegate != null) {
                    mDelegate.onCallReceiverResponse(phone);
                    dismiss();
                }
                break;
        }
    }

    public void updateText(String phone) {
        this.phone = phone;
    }

    public void updateTextPhoneSender(String phoneSender) {
        this.numberSender = phoneSender;
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
    public void onEventPhoneSender(CustomNumberSender customNumberSender) {
        numberSender = customNumberSender.getMessage();
    }

}
