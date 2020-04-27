package com.ems.dingdong.calls.calling;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.calls.diapad.DiapadPresenter;
import com.ems.dingdong.utiles.Constants;
import com.stringee.call.StringeeCall;

import java.util.HashMap;

public class CallingPresenter extends Presenter<CallingContract.View, CallingContract.Interactor> implements CallingContract.Presenter {

    private HashMap<String, StringeeCall> callHashMap;
    private String callId;
    private int type;
    private String callerNumber;
    private String calleeNumber;

    public CallingPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public CallingPresenter setCallHashMap(HashMap<String, StringeeCall> callHashMap) {
        this.callHashMap = callHashMap;
        return this;
    }

    public CallingPresenter setCallId(String callId) {
        this.callId = callId;
        return this;
    }

    public CallingPresenter setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public void setCallType(int type) {
        this.type = type;
    }

    public CallingPresenter setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
        return this;
    }

    public CallingPresenter setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
        return this;
    }

    @Override
    public CallingContract.Interactor onCreateInteractor() {
        return new CallingInteractor(this);
    }

    @Override
    public CallingContract.View onCreateView() {
        return CallingFragment.getInstance();
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

    @Override
    public void openDiapadScreen() {
        new DiapadPresenter(mContainerView).pushView();
    }
}
