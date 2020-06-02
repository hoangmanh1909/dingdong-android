package com.ems.dingdong.calls.calling;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.stringee.call.StringeeCall;
import com.stringee.listener.StatusListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class CallingFragment extends ViewFragment<CallingContract.Presenter> implements CallingContract.View {

    public static final String TAG = CallingFragment.class.getName();

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
    private boolean isHold = false;
    private AudioManager audioManager;

    private StringeeCall stringeeAnswer;
    private StringeeCall mStringeeCall;

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
        audioManager = (AudioManager) getViewContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setMicrophoneMute(false);
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            changeCallLayout(Constants.CALL_TYPE_CALLING);
            ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
            SharedPref pref = SharedPref.getInstance(getViewContext());
            String callFrom = pref.getString(Constants.KEY_ID_FROM_CALLING, "");
            mStringeeCall = new StringeeCall(getViewContext(), applicationController.getStringleeClient(), callFrom, mPresenter.getCalleeNumber());
            mStringeeCall.setCallListener(callListener);
            mStringeeCall.makeCall();
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
                    changeCallLayout(Constants.CALL_TYPE_CALLING);
                    stringeeAnswer.answer();
                    mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
                    mPresenter.openDiapadScreen();
                }
                break;

            case R.id.iv_call_cancel:
                Log.d(TAG, "iv_speaker clicked");
                if (stringeeAnswer != null && mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    stringeeAnswer.reject();
                    getViewContext().finish();
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && mStringeeCall != null) {
                    try {
                        if (mStringeeCall.isSpeakerPhoneOn()) {
                            Log.d(TAG, "CALL_TYPE_CALLING SpeakerPhoneOn");
                            mStringeeCall.setSpeakerphoneOn(false);
                            ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                        } else {
                            Log.d(TAG, "CALL_TYPE_CALLING SpeakerPhoneOff");
                            mStringeeCall.setSpeakerphoneOn(true);
                            ivCallCancel.setImageResource(R.drawable.ic_speaker_green);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && stringeeAnswer != null) {
                    if (stringeeAnswer.isSpeakerPhoneOn()) {
                        Log.d(TAG, "CALL_TYPE_RECEIVING SpeakerPhoneOn");
                        stringeeAnswer.setSpeakerphoneOn(false);
                        ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                    } else {
                        Log.d(TAG, "CALL_TYPE_RECEIVING SpeakerPhoneOn");
                        stringeeAnswer.setSpeakerphoneOn(true);
                        ivCallCancel.setImageResource(R.drawable.ic_speaker_green);
                    }
                }
                break;

            case R.id.iv_call_end:
                if (mStringeeCall != null) {
                    mStringeeCall.hangup();
                } else if (stringeeAnswer != null) {
                    stringeeAnswer.hangup();
                }
                getViewContext().finish();
                break;

            case R.id.iv_mic_off:
                if (audioManager.isMicrophoneMute()) {
                    audioManager.setMicrophoneMute(false);
                    ivMicOff.setImageResource(R.drawable.ic_mic_off);
                } else {
                    audioManager.setMicrophoneMute(true);
                    ivMicOff.setImageResource(R.drawable.ic_mic_off_green);
                }
                break;

            case R.id.iv_foward:
                break;

            case R.id.iv_hold:
                Log.d(TAG, "iv_hold clicked");
                if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && mStringeeCall != null) {
                    if (isHold) {
                        Log.d(TAG, "CALL_TYPE_CALLING isHold on");
                        ivHold.setImageResource(R.drawable.ic_phone_paused);
                        mStringeeCall.unHold(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "hold success isHold off");
                                isHold = false;
                            }
                        });
                    } else {
                        Log.d(TAG, "CALL_TYPE_CALLING SpeakerPhoneOff");
                        ivHold.setImageResource(R.drawable.ic_phone_paused_green);
                        mStringeeCall.hold(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "hold success isHold on");
                                isHold = true;
                            }
                        });
                    }
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && stringeeAnswer != null) {
                    if (isHold) {
                        Log.d(TAG, "CALL_TYPE_RECEIVING SpeakerPhoneOn");
                        ivHold.setImageResource(R.drawable.ic_phone_paused);
                        stringeeAnswer.unHold(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "hold success isHold off");
                                isHold = false;
                            }
                        });

                    } else {
                        Log.d(TAG, "CALL_TYPE_RECEIVING SpeakerPhoneOn");
                        ivHold.setImageResource(R.drawable.ic_phone_paused_green);
                        stringeeAnswer.hold(new StatusListener() {
                            @Override
                            public void onSuccess() {
                                isHold = true;
                                Log.d(TAG, "hold success isHold on");
                            }
                        });
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Can't not find any event!");
        }

    }


    private StringeeCall.StringeeCallListener callListener = new StringeeCall.StringeeCallListener() {
        @Override
        public void onSignalingStateChange(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s, int i, String s1) {
            Logger.d(TAG, "onSignalingStateChange" + signalingState.name());
            tvCalling.setText(signalingState.name());
            new Handler(getViewContext().getMainLooper()).post(() -> {
                if (signalingState.name().equals("ANSWERED")) {
                    chronometer.setVisibility(View.VISIBLE);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    tvCalling.setVisibility(View.GONE);
                    if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING && mStringeeCall != null) {
                        mStringeeCall = stringeeCall;
                    } else {
                        stringeeAnswer = stringeeCall;
                    }
                } else if (signalingState.name().equals("ENDED")) {
                    getViewContext().finish();
                    chronometer.setVisibility(View.GONE);
                    chronometer.stop();
                    tvCalling.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void onError(StringeeCall stringeeCall, int i, String s) {
            showErrorToast("Có lỗi xảy ra không thể thực hiện cuộc gọi");
            getViewContext().finish();
        }

        @Override
        public void onHandledOnAnotherDevice(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s) {
            Logger.d(TAG, "onHandledOnAnotherDevice" + s + signalingState.name());
        }

        @Override
        public void onMediaStateChange(StringeeCall stringeeCall, StringeeCall.MediaState mediaState) {
            Logger.d(TAG, "onMediaStateChange" + mediaState.name());
        }

        @Override
        public void onLocalStream(StringeeCall stringeeCall) {
            Logger.d(TAG, "onLocalStream");

        }

        @Override
        public void onRemoteStream(StringeeCall stringeeCall) {
            Logger.d(TAG, "onRemoteStream");
        }

        @Override
        public void onCallInfo(StringeeCall stringeeCall, JSONObject jsonObject) {
            Logger.d(TAG, "onCallInfo" + jsonObject.toString());
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
