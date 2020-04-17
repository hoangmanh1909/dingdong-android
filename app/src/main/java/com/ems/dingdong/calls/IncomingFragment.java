package com.ems.dingdong.calls;

import android.os.Handler;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.views.CustomTextView;
import com.stringee.call.StringeeCall;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class IncomingFragment extends ViewFragment<IncomingCallContract.Presenter> implements IncomingCallContract.View {
    @BindView(R.id.iv_call_answer)
    ImageView ivCallAnswer;
    @BindView(R.id.iv_call_cancel)
    ImageView ivCallCancel;
    @BindView(R.id.iv_call_end)
    ImageView ivCallEnd;
    @BindView(R.id.tv_time)
    Chronometer chronometer;
    @BindView(R.id.tv_phone_number)
    CustomTextView tvPhoneNumber;
    @BindView(R.id.tv_calling)
    CustomTextView tvCalling;
    private StringeeCall stringeeAnswer;
    private StringeeCall stringeeCall;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calling;
    }

    public static IncomingFragment getInstance() {
        return new IncomingFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            ivCallAnswer.setVisibility(View.GONE);
            ivCallCancel.setVisibility(View.GONE);
            ivCallEnd.setVisibility(View.VISIBLE);
            ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
            stringeeCall = new StringeeCall(getViewContext(), applicationController.getStringleeClient(), "0961979594", mPresenter.getCalleeNumber());
            stringeeCall.setCallListener(callListener);
            stringeeCall.makeCall();
        } else {
            ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
            stringeeAnswer = applicationController.getmStringeeCall();
            stringeeAnswer.setCallListener(callListener);
            ivCallAnswer.setVisibility(View.VISIBLE);
            ivCallCancel.setVisibility(View.VISIBLE);
            ivCallEnd.setVisibility(View.GONE);
            tvPhoneNumber.setText(stringeeAnswer.getFrom());
        }
    }

    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.iv_call_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call_answer:
                if (stringeeAnswer != null && mPresenter.getCallType() == 0) {
                    stringeeAnswer.answer();
                    ivCallEnd.setVisibility(View.VISIBLE);
                    ivCallCancel.setVisibility(View.GONE);
                    ivCallAnswer.setVisibility(View.GONE);
                }
                break;

            case R.id.iv_call_cancel:
                if (stringeeAnswer != null && mPresenter.getCallType() == 0) {
                    stringeeAnswer.reject();
                }
                getViewContext().finish();
                break;

            case R.id.iv_call_end:
                if (mPresenter.getCallType() == 1 && stringeeCall != null) {
                    stringeeCall.hangup();
                } else if (mPresenter.getCallType() == 0 && stringeeAnswer != null) {
                    stringeeAnswer.hangup();
                }
                getViewContext().finish();
                break;
            default:
                throw new IllegalArgumentException("Can't not find any event!");
        }

    }


    private StringeeCall.StringeeCallListener callListener = new StringeeCall.StringeeCallListener() {
        @Override
        public void onSignalingStateChange(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s, int i, String s1) {
            Logger.d("chauvp", "onSignalingStateChange" + signalingState.name());
            tvCalling.setText(signalingState.name());
        }

        @Override
        public void onError(StringeeCall stringeeCall, int i, String s) {
            Logger.d("chauvp", "onError " + s);

        }

        @Override
        public void onHandledOnAnotherDevice(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s) {
            Logger.d("chauvp", "onHandledOnAnotherDevice" + s + signalingState.name());
        }

        @Override
        public void onMediaStateChange(StringeeCall stringeeCall, StringeeCall.MediaState mediaState) {
            Logger.d("chauvp", "onMediaStateChange" + mediaState.name());
            new Handler(getViewContext().getMainLooper()).post(() -> {
                if (mediaState.name().equals("CONNECTED")) {
                    chronometer.start();
                } else if (mediaState.name().equals("ENDED")) {
                    getViewContext().finish();
                }
            });
        }

        @Override
        public void onLocalStream(StringeeCall stringeeCall) {
            Logger.d("chauvp", "onLocalStream");

        }

        @Override
        public void onRemoteStream(StringeeCall stringeeCall) {
            Logger.d("chauvp", "onRemoteStream");
        }

        @Override
        public void onCallInfo(StringeeCall stringeeCall, JSONObject jsonObject) {
            Logger.d("chauvp", "onCallInfo" + jsonObject.toString());
        }
    };
}
