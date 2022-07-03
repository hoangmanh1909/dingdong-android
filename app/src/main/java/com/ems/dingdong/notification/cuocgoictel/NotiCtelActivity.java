package com.ems.dingdong.notification.cuocgoictel;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.functions.mainhome.notify.detailticket.DetailNotifyPresenter;
import com.ems.dingdong.notification.NotificationPresenter;

public class NotiCtelActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        String ticketCode = "";
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("ticketCode");

        return (ViewFragment) new NotiCtelPresenter(this).setCodeTicket(value1).getFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
