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
