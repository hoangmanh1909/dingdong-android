package com.ems.dingdong.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.NetworkUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.callback.PhoneUpdateCallback;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.model.AccountCtel;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import com.sip.cmc.SipCmc;
import com.sip.cmc.network.Account;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneConectDialogExtend extends Dialog {
    private final PhoneCallback mDelegate;
    private String phone1;
    private Context mContext;
    private String numberSender;

    @BindView(R.id.edt_call_ctel_app_to_app)
    EditText edt_call_ctel_app_to_app;

    @BindView(R.id.tv_somayle)
    TextView tvSomayle;

    @BindView(R.id._ll_cuocgoi)
    LinearLayout _ll_cuocgoi;
    private PortSipService portSipService;

    @SuppressLint("SetTextI18n")
    public PhoneConectDialogExtend(Context context, String phone, PhoneCallback reasonCallback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mDelegate = reasonCallback;
        this.phone1 = phone;
        mContext = context;
        View view = View.inflate(getContext(), R.layout.dialog_phone_connect_extend, null);
        setContentView(view);
        ButterKnife.bind(this, view);
//        tvSomayle.setText("Số máy lẻ : " + SipCmc.getAccountInfo().getName());
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
//            PortSipService.PortSipBinder binder = (PortSipService.PortSipBinder) service;
//            portSipService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.tv_phone_updating, R.id.tv_phone_calling, R.id.tv_phone_messing, R.id.tv_call_CSKH, R.id.tv_phone_update_sender, R.id.btn_call_ctel_app_to_app,
            R.id.btn_call_ctel_operator, R.id.id_login_ctel, R.id.tv_phone_internet, R.id.tv_phone_thuong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_thuong:
                if (mDelegate != null) {
                    mDelegate.onCallReceiverResponse(numberSender);
                    dismiss();
                }
                break;

            case R.id.tv_phone_internet:
                if (TextUtils.isEmpty(phone1)) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(phone1)) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                if (!NetworkUtils.isConnected()) {
                    String tam = ","+phone1;
                    new ThongbaoGoiDialog(getContext(), phone1, new PhoneUpdateCallback() {
                        @Override
                        public void onCall(String phone) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(Constants.HEADER_NUMBER + tam));
                            mContext.startActivity(intent);
                        }
                    }).show();
                    return;
                }
                SipCmc.callTo(phone1);
                Intent intent1 = new Intent(getContext(), IncomingCallActivity.class);
                intent1.putExtra(Constants.CALL_TYPE, 1);
                intent1.putExtra(Constants.KEY_CALLEE_NUMBER, phone1);
                getContext().startActivity(intent1);
                break;

            case R.id.id_login_ctel:
                ApplicationController.getInstance().initPortSipService();
                break;

            case R.id.tv_phone_messing:
                break;

            case R.id.tv_phone_updating:
                if (_ll_cuocgoi.getVisibility() == View.VISIBLE)
                    _ll_cuocgoi.setVisibility(View.GONE);
                else _ll_cuocgoi.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_call_CSKH:
                if (mDelegate != null) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "1900545481"));
                    getContext().startActivity(intent);
                    //getViewContext().startActivity(intent);
//                    if (ContextCompat.checkSelfPermission(getContext(),
//                            Manifest.permission.CALL_PHONE)
//                            != PackageManager.PERMISSION_GRANTED) {
//
//                        ActivityCompat.requestPermissions((Activity) getContext(),
//                                new String[]{Manifest.permission.CALL_PHONE},
//                                00);
//                    } else {
//                        try {
//                            getContext().startActivity(intent);
//                        } catch (SecurityException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    dismiss();
                }
                break;

            case R.id.tv_phone_update_sender:
                new PhoneNumberUpdateSenderDialog(getContext(), phone1, 1, mDelegate, new PhoneUpdateCallback() {
                    @Override
                    public void onCall(String phone) {
                        phone1 = phone;
                    }
                }).show();
                break;

            case R.id.btn_call_ctel_app_to_app:
                if (TextUtils.isEmpty(edt_call_ctel_app_to_app.getText().toString().trim())) {
                    Toast.showToast(getContext(), "Vui lòng nhập số");
                } else {
                    SipCmc.callTo(edt_call_ctel_app_to_app.getText().toString().trim());
                    Intent intent = new Intent(getContext(), IncomingCallActivity.class);
                    intent.putExtra(Constants.CALL_TYPE, 1);
                    intent.putExtra(Constants.APP_TO_APP, 5);
                    intent.putExtra(Constants.KEY_CALLEE_NUMBER, edt_call_ctel_app_to_app.getText().toString().trim());
                    getContext().startActivity(intent);
                }
                break;

            case R.id.btn_call_ctel_operator:
                if (TextUtils.isEmpty(phone1)) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(phone1)) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                if (mDelegate != null) {
                    mDelegate.onCallReceiverResponse(phone1);
                    dismiss();
                }
                break;
        }
    }

    public void updateText(String phone) {
        this.phone1 = phone;
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
