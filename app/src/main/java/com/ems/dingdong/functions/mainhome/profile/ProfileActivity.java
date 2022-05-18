package com.ems.dingdong.functions.mainhome.profile;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.utiles.Log;


/**
 * Created by DieuPC on 3/5/2018.
 */

public class ProfileActivity extends DingDongActivity {
    private ProfileFragment mFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("message");
        Log.d("Asd123123", value1);
        if (value1 != null)
            new DialogTextThanhConhg(this, value1, new DialogCallback() {
                @Override
                public void onResponse(String loginRespone) {
                }
            }).show();
        mFragment = (ProfileFragment) new ProfilePresenter(this).getFragment();
        return mFragment;
    }

}
