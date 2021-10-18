package com.ems.dingdong.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.core.base.ContainerActivity;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.utiles.DialogUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.app.ApplicationController;
//import com.sip.cmc.SipCmc;
//import com.sip.cmc.callback.PhoneCallback;

import org.linphone.core.LinphoneCall;


/**
 * Created by Administrator on 6/8/2017.
 */

public abstract class DingDongActivity extends ContainerActivity {
    protected SharedPref mPref;
    protected ApplicationController mApplicationController = ApplicationController.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = new SharedPref(this);
//        SipCmc.addCallback(null, new PhoneCallback() {
//            @Override
//            public void incomingCall(LinphoneCall linphoneCall) {
//                super.incomingCall(linphoneCall);
//                Log.d("123123khiem", "incomingCall: DingDongActivity ");
//                Intent activityIntent = new Intent(getViewContext(), IncomingCallActivity.class);
//                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(activityIntent);
//            }
//
//            @Override
//            public void outgoingInit() {
//                super.outgoingInit();
//                Log.d("123123khiem", "outgoingInit DingDongActivity");
//            }
//
//            @Override
//            public void callConnected(LinphoneCall linphoneCall) {
//                super.callConnected(linphoneCall);
//                Log.d("123123khiem", String.valueOf(linphoneCall));
//            }
//
//            @Override
//            public void callEnd(LinphoneCall linphoneCall) {
//                super.callEnd(linphoneCall);
//                Log.d("123123khiem", "callEnd DingDongActivity");
//
//                Ring.getInstance(getViewContext()).stopRingTone();
//                SipCmc.hangUp();
//                finish();
//            }
//
//            @Override
//            public void callReleased() {
//                super.callReleased();
//                Log.d("123123khiem", "callReleased DingDongActivity");
//            }
//
//            @Override
//            public void error() {
//                super.error();
//                SipCmc.hangUp();
//                finish();
//                Log.d("123123khiem", "error DingDongActivity");
//            }
//
//            @Override
//            public void callStatus(int status) {
//                super.callStatus(status);
//                Log.d("123123khiem", "callStatus:  DingDongActivity" + status);
//                if (status == 0) {
//
//                } else if (status == 1) {
//                } else if (status == 2) {
//                    //Cúp máy
//                    Ring.getInstance(getViewContext()).stopRingTone();
//                    SipCmc.hangUp();
//                    finish();
//                }
//            }
//
//            @Override
//            public void callTimeRing(String time) {
//                super.callTimeRing(time);
//                Log.d("123123khiem", "callTimeRing DingDongActivity");
//            }
//
//            @Override
//            public void callTimeAnswer(String time) {
//                super.callTimeAnswer(time);
//                Log.d("123123khiem", time);
//            }
//
//            @Override
//            public void callTimeEnd(String time) {
//                super.callTimeEnd(time);
//                Log.d("123123khiem", "callTimeEnd DingDongActivity");
//            }
//
//            @Override
//            public void callId(String callId) {
//                super.callId(callId);
//                Log.d("123123khiem", "callId DingDongActivity");
//            }
//
//            @Override
//            public void callPhoneNumber(String phoneNumber) {
//                super.callPhoneNumber(phoneNumber);
//                Log.d("123123khiem", "callPhoneNumber: " + phoneNumber);
//            }
//
//            @Override
//            public void callDuration(long duration) {
//                super.callDuration(duration);
//                Log.d("123123khiem", "callDuration: DingDongActivity" + duration);
//            }
//        });
    }

    public void showAlertDialog(String message) {
        DialogUtils.showAlert(this, message);
    }

    @Override
    public void showAlertDialog(String message, DialogInterface.OnClickListener onClickListener) {

        DialogUtils.showAlertAction(this, message, onClickListener);
    }

    public void showErrorToast(String message) {
        Toast.showToast(this, message);
    }

    public void showSuccessToast(String message) {
        Toast.showToast(this, message);
    }

    public void showProgress() {
        DialogUtils.showProgressDialog(this);
    }

    public boolean isShowing() {
        return DialogUtils.isShowing();
    }

    public void hideProgress() {
        DialogUtils.dismissProgressDialog();
    }

    public void onRequestError(String errorCode, String errorMessage) {
        DialogUtils.showErrorAlert(this, errorMessage);
        hideProgress();
    }

    @Override
    public void showErrorAlert(Context context, String string) {
        DialogUtils.showErrorAlert(context, string);
    }

    @Override
    public void showNetworkErrorDialog(Activity activity) {
        DialogUtils.showNetworkErrorDialog(activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}
