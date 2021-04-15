package com.ems.dingdong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneCallback;
import com.ems.dingdong.functions.mainhome.profile.CustomNumberSender;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneConectDialog extends Dialog {
    private final PhoneCallback mDelegate;
    private String phone;
    private Context mContext;
    private String numberSender;
    /*@BindView(R.id.edt_call_ctel_app_to_app)
    EditText edt_call_ctel_app_to_app;*/

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

    @OnClick({R.id.tv_phone_updating, R.id.tv_phone_calling, R.id.tv_phone_messing/*, R.id.tv_call_CSKH, R.id.tv_phone_update_sender, R.id.btn_call_ctel_app_to_app*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone_updating:
                new PhoneNumberUpdateDialog(getContext(), phone,1, mDelegate).show();
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

                //call receiver
            case R.id.tv_phone_calling:

                if (TextUtils.isEmpty(phone)) {
                    Toast.showToast(mContext, "Xin vui lòng nhập SĐT.");
                    return;
                }
                if (!NumberUtils.checkNumber(phone)) {
                    Toast.showToast(mContext, "Số điện thoại không hợp lệ.");
                    return;
                }
                if (mDelegate != null) {
                    mDelegate.onCallSenderResponse(phone);
                    dismiss();
                }
                break;

           /* case R.id.tv_call_CSKH:
                if (mDelegate != null) {
                    mDelegate.onCallCSKH("1900545481");
                    dismiss();
                }
                break;

            case R.id.tv_phone_update_sender:
                new PhoneNumberUpdateSenderDialog(getContext(), numberSender, mDelegate).show();
                break;

            case R.id.btn_call_ctel_app_to_app:
                Log.d("123123", "click call ctel app to app");
                //SipCmc.callTo(edt_call_ctel_app_to_app.getText().toString());
                break;*/
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
    public void onEventPhoneSender(CustomNumberSender customNumberSender){
        numberSender = customNumberSender.getMessage();
    }

}
