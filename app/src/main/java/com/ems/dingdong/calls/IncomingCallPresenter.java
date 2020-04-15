package com.ems.dingdong.calls;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.utiles.Constants;
import com.stringee.call.StringeeCall;

import java.util.HashMap;

public class IncomingCallPresenter extends Presenter<IncomingCallContract.View, IncomingCallContract.Interactor> implements IncomingCallContract.Presenter {

    private HashMap<String, StringeeCall> callHashMap;
    private String callId;
    private int type;
    private String callerNumber;
    private String calleeNumber;

    public IncomingCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public IncomingCallPresenter setCallHashMap(HashMap<String, StringeeCall> callHashMap) {
        this.callHashMap = callHashMap;
        return this;
    }

    public IncomingCallPresenter setCallId(String callId) {
        this.callId = callId;
        return this;
    }

    public IncomingCallPresenter setType(int type) {
        this.type = type;
        return this;
    }


    public IncomingCallPresenter setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
        return this;
    }

    public IncomingCallPresenter setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
        return this;
    }

    @Override
    public IncomingCallContract.Interactor onCreateInteractor() {
        return new IncomingCallInteractor(this);
    }

    @Override
    public IncomingCallContract.View onCreateView() {
        return IncomingFragment.getInstance();
    }

    @Override
    public StringeeCall getStringeeCall() {
        return callHashMap.get(Constants.CALL_MAP);
    }

    @Override
    public String getCallId() {
        return callId;
    }

    @Override
    public int getCallType() {
        return type;
    }

    @Override
    public String getCallerNumber() {
        return callerNumber;
    }

    @Override
    public String getCalleeNumber() {
        return calleeNumber;
    }
}
