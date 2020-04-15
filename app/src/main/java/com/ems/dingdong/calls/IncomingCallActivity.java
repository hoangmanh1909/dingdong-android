package com.ems.dingdong.calls;

import android.content.Intent;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;
import com.ems.dingdong.utiles.Constants;
import com.stringee.call.StringeeCall;

import java.util.HashMap;

public class IncomingCallActivity extends DingDongActivity {
    @Override
    public ViewFragment onCreateFirstFragment() {
        HashMap<String, StringeeCall> callHashMap = new HashMap<>();
        String callId = "";
        int type = 0;
        String callerNumber = "";
        String calleeNumber = "";
        Intent intent = getIntent();
        if (intent != null) {
            callHashMap = (HashMap<String, StringeeCall>) intent.getSerializableExtra(Constants.CALL_MAP);
            callId = intent.getStringExtra(Constants.CALL_ID);
            type = intent.getIntExtra(Constants.CALL_TYPE, 0);
            callerNumber = intent.getStringExtra(Constants.KEY_CALLER_NUMBER);
            calleeNumber = intent.getStringExtra(Constants.KEY_CALLEE_NUMBER);
        }
        return (ViewFragment) new IncomingCallPresenter(this)
                .setCallHashMap(callHashMap)
                .setCallId(callId)
                .setType(type)
                .setCallerNumber(callerNumber)
                .setCalleeNumber(calleeNumber)
                .getFragment();
    }
}
