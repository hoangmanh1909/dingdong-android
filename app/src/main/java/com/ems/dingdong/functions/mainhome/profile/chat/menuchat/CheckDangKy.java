package com.ems.dingdong.functions.mainhome.profile.chat.menuchat;

import android.content.Context;

import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;

public class CheckDangKy {

    public static boolean isCheckUserChat(Context context) {
        SharedPref sharedPref = new SharedPref(context);
        String userJson = sharedPref.getString(com.ems.dingdong.utiles.Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        if (userInfo.getChatUserName().isEmpty()) {
            return false;
        }
        return true;
    }
}
