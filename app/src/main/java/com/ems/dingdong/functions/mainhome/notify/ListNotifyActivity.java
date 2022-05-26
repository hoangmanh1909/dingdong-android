package com.ems.dingdong.functions.mainhome.notify;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.callback.DialogCallback;
import com.ems.dingdong.dialog.DialogTextThanhConhg;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;

public class ListNotifyActivity extends DingDongActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        Intent intent = getIntent();
        String value2 = intent.getStringExtra("message");
        if (value2 != null)
            new DialogTextThanhConhg(this, value2, new DialogCallback() {
                @Override
                public void onResponse(String loginRespone) {
                }
            }).show();
        return (ViewFragment) new ListNotifyPresenter(this).getFragment();
    }
}
