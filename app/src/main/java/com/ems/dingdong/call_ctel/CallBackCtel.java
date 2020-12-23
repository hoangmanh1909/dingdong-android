package com.ems.dingdong.call_ctel;

import com.ems.dingdong.utiles.Log;
import com.sip.cmc.SipCmc;
import com.sip.cmc.callback.RegistrationCallback;

public class CallBackCtel extends RegistrationCallback {


    @Override
    public void registrationOk() {
        super.registrationOk();
        Log.d("123123", "registrationOk");

    }

    @Override
    public void registrationFailed() {
        super.registrationFailed();
        Log.d("123123", "registrationFailed");
    }
}
