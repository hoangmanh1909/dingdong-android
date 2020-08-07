package com.ems.dingdong.callback;

import android.graphics.Bitmap;

public interface SignCallback {
    void onResponse(String realName, String sign,Bitmap bitmap);
}
