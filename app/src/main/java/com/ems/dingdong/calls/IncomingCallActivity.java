package com.ems.dingdong.calls;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.calls.calling.CallingPresenter;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;

public class IncomingCallActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        int type = 0;
        String callerNumber = "";
        String calleeNumber = "";
        long sessionId= 0;
        Intent intent = getIntent();
        Logger.d("chauvp", "IncomingCallActivity");
        if (intent != null) {
            type = intent.getIntExtra(Constants.CALL_TYPE, 0);
            callerNumber = intent.getStringExtra(Constants.KEY_CALLER_NUMBER);
            calleeNumber = intent.getStringExtra(Constants.KEY_CALLEE_NUMBER);
            sessionId = intent.getLongExtra(Constants.SESSION_ID, 0);
        }
        return (ViewFragment) new CallingPresenter(this)
                .setType(type)
                .setCallerNumber(callerNumber)
                .setCalleeNumber(calleeNumber)
                .setSessionId(sessionId)
                .getFragment();
    }
}
