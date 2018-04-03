package com.vinatti.dingdong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;


import com.core.base.ContainerActivity;
import com.vinatti.dingdong.app.ApplicationController;
import com.vinatti.dingdong.utiles.DialogUtils;
import com.vinatti.dingdong.utiles.SharedPref;

import java.io.Serializable;
import java.util.List;


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
        DialogUtils.showErrorAlert(this, message);
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
