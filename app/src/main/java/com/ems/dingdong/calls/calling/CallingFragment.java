package com.ems.dingdong.calls.calling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.PortMessageReceiver;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.diapad.DiapadFragment;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.portsip.PortSipSdk;
import butterknife.BindView;
import butterknife.OnClick;

public class CallingFragment extends ViewFragment<CallingContract.Presenter> implements CallingContract.View, PortMessageReceiver.BroadcastListener {

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
    private AudioManager audioManager;
    private PortSipSdk portSipSdk;
    private Session session;
    private CallingEvent callingEvent = new CallingEvent();
    long mSessionId;

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
//        audioManager.setMicrophoneMute(false);
        ///session = CallManager.Instance().getSession();
        session = CallManager.Instance().getCurrentSession();
        CallManager.Instance().reset();
        ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
        portSipSdk = applicationController.portSipSdk;
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            changeCallLayout(Constants.CALL_TYPE_CALLING);
            try {
                session.sessionID = portSipSdk.call(mPresenter.getCalleeNumber(), true, false);
            } catch (NullPointerException nullPointerException) {
            }
            ///session.sessionID = portSipSdk.call(mPresenter.getCalleeNumber(), true, false);
        } else {
            try {
                //tvPhoneNumber.setText(CallManager.Instance().getSession().phoneNumber);
                changeCallLayout(Constants.CALL_TYPE_RECEIVING);
            } catch (NullPointerException nullPointerException) {
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewContext().registerReceiver(callingEvent, new IntentFilter(PortSipService.ACTION_CALL_EVENT));
    }

    @Override
    public void onDestroy() {
        if (callingEvent != null)
            getViewContext().unregisterReceiver(callingEvent);
        super.onDestroy();
    }

    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.iv_call_end, R.id.iv_hold, R.id.iv_foward, R.id.iv_mic_off})
    public void onViewClicked(View view) {

        Session currentLine = CallManager.Instance().getCurrentSession();

        switch (view.getId()) {
            case R.id.iv_call_answer:
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    changeCallLayout(Constants.CALL_TYPE_CALLING);
                    portSipSdk.answerCall(session.sessionID, false);
                    mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                } else {
                    new DiapadFragment(getViewContext(), number -> {
                        if (portSipSdk != null && session.sessionID != Session.INVALID_SESSION_ID) {
                            portSipSdk.hangUp(session.sessionID);
                        }
                        tvPhoneNumber.setText(number);
                        session.sessionID = portSipSdk.call(number, true, false);
                    }).show();
                }
                break;

            case R.id.iv_call_cancel:
                Log.d(TAG, "iv_speaker clicked");
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    portSipSdk.rejectCall(session.sessionID, 486);
                    portSipSdk.hangUp(session.sessionID);
                    Ring.getInstance(getViewContext()).stopRingTone();
                    currentLine.reset();
                    getViewContext().finish();
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
                    if (audioManager.isSpeakerphoneOn()) {
                        audioManager.setSpeakerphoneOn(false);
                        ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                    } else {
                        audioManager.setSpeakerphoneOn(true);
                        ivCallCancel.setImageResource(R.drawable.ic_speaker_green);
                    }
                }
                break;

            case R.id.iv_call_end:
                Ring.getInstance(getActivity()).stop();
                if (portSipSdk != null) {
                    try {
                        portSipSdk.hangUp(currentLine.sessionID);
                        portSipSdk.rejectCall(currentLine.sessionID, 486);
                        Log.d("123123", "session1.sessionID: " + session.sessionID);
                    } catch (NullPointerException nullPointerException) {
                    }
                }

                currentLine.reset();
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
                if (session.isHold) {
                    int result = portSipSdk.unHold(session.sessionID);
                    if (result == 0) {
                        session.isHold = false;
                        ivHold.setImageResource(R.drawable.ic_phone_paused);
                    }
                } else {
                    int result = portSipSdk.hold(session.sessionID);
                    if (result == 0) {
                        session.isHold = true;
                        ivHold.setImageResource(R.drawable.ic_phone_paused_green);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Can't not find any event!");
        }

    }

    private void changeCallLayout(int type) {
        if (type == Constants.CALL_TYPE_CALLING) {
            ivCallAnswer.setImageResource(R.drawable.ic_dialpad);
            ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
            ivCallEnd.setVisibility(View.VISIBLE);
            ivMicOff.setVisibility(View.VISIBLE);
            ivFoward.setVisibility(View.VISIBLE);
            ivHold.setVisibility(View.VISIBLE);
        } else {
            tvPhoneNumber.setText(session.displayName);///
            ivCallAnswer.setImageResource(R.drawable.ic_call_hang_green);
            ivCallCancel.setImageResource(R.drawable.ic_call_end_red);
            ivCallEnd.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
            ivFoward.setVisibility(View.GONE);
            ivHold.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBroadcastReceiver(Intent intent) {
        String action = intent.getAction();
        if (PortSipService.CALL_CHANGE_ACTION.equals(action))
        {
            long sessionId = intent.getLongExtra(PortSipService.EXTRA_CALL_SEESIONID, Session.INVALID_SESSION_ID);
            Session session = CallManager.Instance().findSessionBySessionID(sessionId);
            if (session != null)
            {
                switch (session.state)
                {
                    case INCOMING:
                        break;
                    case TRYING:
                        break;
                    case CONNECTED:
                    case FAILED:
                    case CLOSED:
                        Session anOthersession = CallManager.Instance().findIncomingCall();
                        if(anOthersession==null) {
                            getActivity().finish();
                        }/*else{
                            sessionId = anOthersession.sessionID;
                        }*/
                        break;
                }
            }
        }
    }


    class CallingEvent extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(PortSipService.ACTION_CALL_EVENT)) {
                switch (intent.getStringExtra(PortSipService.TYPE_ACTION)) {
                    case PortSipService.CALL_EVENT_RINGING:
                        tvCalling.setText("Ringing");
                        break;
                    case PortSipService.CALL_EVENT_TRYING:
                        tvCalling.setText("Calling");
                        break;
                    case PortSipService.CALL_EVENT_ANSWER:
                        Ring.getInstance(getViewContext()).stopRingTone();
                        break;
                    case PortSipService.CALL_EVENT_FAILURE:
                        tvCalling.setText("failure");
                        chronometer.stop();
                        tvCalling.setVisibility(View.VISIBLE);
                        getActivity().finish();
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_REJECT:
                        tvCalling.setText("busy");
                        break;
                    case PortSipService.CALL_EVENT_INVITE_COMMING:
                        //tvCalling.setText(session.phoneNumber);
                        changeCallLayout(Constants.CALL_TYPE_RECEIVING);
                        break;
                    case PortSipService.CALL_EVENT_END:
                        tvCalling.setText("call ended");
                        chronometer.stop();
                        tvCalling.setVisibility(View.VISIBLE);
                        chronometer.setVisibility(View.GONE);
                        changeCallLayout(Constants.CALL_TYPE_CALLING);
                        Ring.getInstance(getViewContext()).stopRingTone();
                        getActivity().finish();
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_ACCEPT:
                        tvCalling.setText("accepted");
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        break;
                    case PortSipService.CALL_EVENT_INVITE_CONNECTED:
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        Ring.getInstance(getViewContext()).stopRingTone();
                        break;
                    default:
                        throw new IllegalArgumentException("error occurred when get event");
                }
            }
        }
    }
}
