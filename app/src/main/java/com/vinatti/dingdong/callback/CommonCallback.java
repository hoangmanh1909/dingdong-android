package com.vinatti.dingdong.callback;

import android.accounts.AuthenticatorException;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;


import com.vinatti.dingdong.R;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.utiles.DialogUtils;
import com.vinatti.dingdong.utiles.Log;
import com.vinatti.dingdong.utiles.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.channels.NoConnectionPendingException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommonCallback<T extends SimpleResult> implements Callback<T> {
    private Context mActivity;
    private static final String TAG = CommonCallback.class.getSimpleName();
    private boolean isDismissProgress = true;

    private boolean isInternalErrorDisplayed = true;

    public CommonCallback(Context activity) {
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

    protected void onError(Call<T> call, String message) {
        if (isDismissProgress) {
            DialogUtils.dismissProgressDialog();
        }
    }

    public void setDismissProgress(boolean dismissProgress) {
        isDismissProgress = dismissProgress;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.code() >= 200 && response.code() < 300 && response.body() != null) {
            onSuccess(call, response);
        } else if (response != null && (response.code() < 200 || response.code() >= 300)) {
            if (mActivity != null && isInternalErrorDisplayed) {
                this.onError(call, mActivity.getString(R.string.error_system_upgrading));
            } else {
                this.onError(call, "");
            }
        } else {
            if (mActivity != null && this.isInternalErrorDisplayed) {
                this.onError(call, mActivity.getString(R.string.error_fail_default));
            } else {
                this.onError(call, "");
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable error) {
        if (mActivity != null && this.isInternalErrorDisplayed) {
            Toast.showToast(mActivity, R.string.error_fail_default);
        }
        Log.e("LOG: ", error.toString());
        if (error instanceof TimeoutException || error instanceof NoConnectionPendingException) {
            this.onError(call, "Thời gian kết nối đến máy chủ quá lâu");
        } else if (error instanceof AuthenticatorException) {
            this.onError(call, "Lỗi xác thực tới máy chủ");
        } else if (error instanceof SocketTimeoutException || error instanceof TimeoutException || error instanceof ConnectException) {
            this.onError(call, "Không thể kết nối đến máy chủ");
        } else if (error instanceof NetworkErrorException) {
            this.onError(call, "Vui lòng kiểm tra lại kết nối mạng");
        } else if (error instanceof ParseException) {
            this.onError(call, "Parse error");
        } else {
            this.onError(call, "Lỗi kết nối hệ thống");
        }
    }
}
