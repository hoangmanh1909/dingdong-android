package com.ems.dingdong.calls.calling;

import android.os.Handler;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.stringee.call.StringeeCall;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class CallingFragment extends ViewFragment<CallingContract.Presenter> implements CallingContract.View {
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
    @BindView(R.id.iv_foward)
    CustomImageView ivFoward;
    @BindView(R.id.iv_hold)
    CustomImageView ivHold;
    @BindView(R.id.iv_mic_off)
    CustomImageView ivMicOff;

    private StringeeCall stringeeAnswer;
    private StringeeCall stringeeCall;
    private boolean isSpeakerOn;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calling;
    }

    public static CallingFragment getInstance() {
        return new CallingFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            changeCallLayout(Constants.CALL_TYPE_CALLING);
            ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
            stringeeCall = new StringeeCall(getViewContext(), applicationController.getStringleeClient(), "VPBX_VP1_100", "VPBX_MVNP_100");
            stringeeCall.setCallListener(callListener);
            stringeeCall.makeCall();
        } else {
            ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
            stringeeAnswer = applicationController.getmStringeeCall();
            stringeeAnswer.setCallListener(callListener);
            changeCallLayout(Constants.CALL_TYPE_RECEIVING);
            tvPhoneNumber.setText(stringeeAnswer.getFrom());
        }
    }

    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.iv_call_end, R.id.iv_hold, R.id.iv_foward, R.id.iv_mic_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_call_answer:
                if (stringeeAnswer != null && mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    stringeeAnswer.answer();
                    changeCallLayout(Constants.CALL_TYPE_RECEIVING);
                    mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
                    mPresenter.openDiapadScreen();
                }
                break;

            case R.id.iv_call_cancel:
                if (stringeeAnswer != null && mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    stringeeAnswer.reject();
                    getViewContext().finish();
                } else if (stringeeCall != null && mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
//                    if (stringeeCall.isSpeakerPhoneOn()) {
//                        ivCallAnswer.setImageResource(R.drawable.ic_button_speaker_green);
//                    } else {
//                        ivCallAnswer.setImageResource(R.drawable.ic_button_speaker);
//                    }
//                    stringeeCall.setSpeakerphoneOn(!stringeeCall.isSpeakerPhoneOn());
                }
                if (isSpeakerOn) {
                    ivCallCancel.setImageResource(R.drawable.ic_button_speaker_green);
                    isSpeakerOn = false;
                } else {
                    ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                    isSpeakerOn = true;
                }
                break;

            case R.id.iv_call_end:
                if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && stringeeCall != null) {
                    stringeeCall.hangup();
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING && stringeeAnswer != null) {
                    stringeeAnswer.hangup();
                }
                getViewContext().finish();
                break;

            case R.id.iv_mic_off:
                ivMicOff.setSelected(!ivMicOff.getSelected());
                ivMicOff.setBackgroundImage();
                break;

            case R.id.iv_foward:
                if (ivFoward.getSelected()) {
                    ivFoward.setBackgroundColor(getResources().getColor(R.color.bg_color_diapad_button));
                } else {
                    ivFoward.setBackgroundColor(getResources().getColor(R.color.white));
                }
                ivFoward.setSelected(!ivFoward.getSelected());
                break;

            case R.id.iv_hold:
                if (ivHold.getSelected()) {
                    ivHold.setBackgroundColor(getResources().getColor(R.color.bg_color_diapad_button));
                } else {
                    ivHold.setBackgroundColor(getResources().getColor(R.color.white));
                }
                ivHold.setSelected(!ivHold.getSelected());
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

    private void changeCallLayout(int type) {
        if (type == Constants.CALL_TYPE_CALLING) {
            ivCallAnswer.setImageResource(R.drawable.ic_dialpad);
            ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
            ivCallEnd.setVisibility(View.VISIBLE);
            ivMicOff.setVisibility(View.VISIBLE);
            ivFoward.setVisibility(View.VISIBLE);
            ivHold.setVisibility(View.VISIBLE);
        } else {
            ivCallAnswer.setImageResource(R.drawable.ic_call_hang_green);
            ivCallCancel.setImageResource(R.drawable.ic_call_end_red);
            ivCallEnd.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
            ivFoward.setVisibility(View.GONE);
            ivHold.setVisibility(View.GONE);
        }
    }
}
