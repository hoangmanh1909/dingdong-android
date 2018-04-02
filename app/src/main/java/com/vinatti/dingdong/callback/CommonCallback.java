package com.vinatti.dingdong.callback;

import android.app.Activity;


import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.utiles.DialogUtils;
import com.vinatti.dingdong.utiles.Log;
import com.vinatti.dingdong.utiles.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommonCallback<T extends SimpleResult> implements Callback<T> {
    private Activity mActivity;
    private static final String TAG = CommonCallback.class.getSimpleName();
    private boolean isDismissProgress = true;

    private boolean isInternalErrorDisplayed = true;

    public CommonCallback(Activity activity) {
        this.isInternalErrorDisplayed = false;
        mActivity = activity;
    }

    protected CommonCallback(Activity activity, boolean isInternalErrorDisplayed) {
        this.isInternalErrorDisplayed = isInternalErrorDisplayed;
        mActivity = activity;
    }


    protected void onSuccess(Call<T> call, Response<T> response) {
        if (isDismissProgress) {
            DialogUtils.dismissProgressDialog();
        }
    }

    protected void onError(Call<T> call) {
        if (isDismissProgress) {
            DialogUtils.dismissProgressDialog();
        }
    }

    public void setDismissProgress(boolean dismissProgress) {
        isDismissProgress = dismissProgress;
    }

    public Activity getActivity() {
        return mActivity;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.code() >= 200 && response.code() < 300 && response.body() != null) {
            onSuccess(call, response);
        } else if (response != null && (response.code() < 200 || response.code() >= 300)) {
            if (mActivity != null && isInternalErrorDisplayed) {
                android.widget.Toast.makeText(mActivity, R.string.error_system_upgrading, android.widget.Toast.LENGTH_LONG).show();
            }
            this.onError(call);
        } else {
            if (mActivity != null && this.isInternalErrorDisplayed) {
                Toast.showToast(mActivity, R.string.error_fail_default);
            }
            this.onError(call);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mActivity != null && this.isInternalErrorDisplayed) {
            Toast.showToast(mActivity, R.string.error_fail_default);
        }
        Log.e("LOG: ", t.toString());
        this.onError(call);
    }
}
