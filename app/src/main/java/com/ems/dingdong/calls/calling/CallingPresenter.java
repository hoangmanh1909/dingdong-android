package com.ems.dingdong.calls.calling;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.calls.diapad.DiapadFragment;

public class CallingPresenter extends Presenter<CallingContract.View, CallingContract.Interactor> implements CallingContract.Presenter {

    private String callId;
    private int type;
    private String callerNumber;
    private String calleeNumber;
    private long sessionId;

    public CallingPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public CallingPresenter setCallId(String callId) {
        this.callId = callId;
        return this;
    }

    public CallingPresenter setType(int type) {
        this.type = type;
        return this;
    }

    public CallingPresenter setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
        return this;
    }

    public CallingPresenter setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
        return this;
    }

    public CallingPresenter setSessionId(Long sessionId){
        this.sessionId = sessionId;
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
    public void setCallType(int type) {
        this.type = type;
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
    public Long getSessionId() {
        return sessionId;
    }

}
