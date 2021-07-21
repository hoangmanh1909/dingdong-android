package com.ems.dingdong.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.ems.dingdong.R;
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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThongbaoGoiDialog extends Dialog {
    private final PhoneUpdateCallback mDelegate;
    private String phone;
    private Context mContext;
    private String numberSender;

    private PortSipService portSipService;

    @SuppressLint("SetTextI18n")
    public ThongbaoGoiDialog(Context context, String phone, PhoneUpdateCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone = phone;
        mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_icon_1, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_phone_calling, R.id.tv_huy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_calling:
                mDelegate.onCall(phone);
                dismiss();
                break;
            case R.id.tv_huy:
                dismiss();
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
