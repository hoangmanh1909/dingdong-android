package com.ems.dingdong.network;

import android.accounts.AuthenticatorException;
import android.accounts.NetworkErrorException;
import android.content.Context;

import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.channels.NoConnectionPendingException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;


public class ApiDisposable {
    public ApiDisposable(Throwable throwable, Context context) {
        if (throwable instanceof TimeoutException || throwable instanceof NoConnectionPendingException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Thời gian kết nối đến máy chủ quá lâu", Constants.ERROR).show();
        } else if (throwable instanceof AuthenticatorException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Lỗi xác thực tới máy chủ", Constants.ERROR).show();
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException || throwable instanceof ConnectException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Không thể kết nối đến máy chủ", Constants.ERROR).show();
        } else if (throwable instanceof NetworkErrorException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Vui lòng kiểm tra lại kết nối mạng", Constants.ERROR).show();
        } else if (throwable instanceof ParseException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Parse throwable", Constants.ERROR).show();
        } else if (throwable instanceof JsonSyntaxException) {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Dữ liệu trả về sai cấu trúc", Constants.ERROR).show();
        } else {
            CustomToast.makeText(context, (int) CustomToast.LONG, "Lỗi kết nối hệ thống " + throwable.getMessage(), Constants.ERROR).show();
        }
    }
}