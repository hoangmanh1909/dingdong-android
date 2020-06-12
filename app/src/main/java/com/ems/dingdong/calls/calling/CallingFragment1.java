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
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.portsip.PortSipSdk;

import butterknife.BindView;
import butterknife.OnClick;

public class CallingFragment1 extends ViewFragment<CallingContract.Presenter> implements CallingContract.View {

    public static final String TAG = CallingFragment1.class.getName();

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
    private PortSipSdk portSipSdk;
    private long mSessionId;
    private CallingEvent callingEvent = new CallingEvent();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calling;
    }

    public static CallingFragment1 getInstance() {
        return new CallingFragment1();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        audioManager = (AudioManager) getViewContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
//        audioManager.setMicrophoneMute(false);
        ApplicationController applicationController = (ApplicationController) getViewContext().getApplication();
        portSipSdk = applicationController.portSipSdk;
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            changeCallLayout(Constants.CALL_TYPE_CALLING);
            mSessionId = portSipSdk.call(mPresenter.getCalleeNumber(), true, false);
        } else {
            mSessionId = mPresenter.getSessionId();
            Log.d(TAG, "Sessionid: " + mSessionId);
            changeCallLayout(Constants.CALL_TYPE_RECEIVING);
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
        switch (view.getId()) {
            case R.id.iv_call_answer:
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    changeCallLayout(Constants.CALL_TYPE_CALLING);
                    portSipSdk.answerCall(mSessionId, false);
                    mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                } else if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
                    mPresenter.openDiapadScreen();
                }
                break;

            case R.id.iv_call_cancel:
                Log.d(TAG, "iv_speaker clicked");
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    int result = portSipSdk.rejectCall(mSessionId, 480);
                    Log.d(TAG, "reject result: " + result);
                    getViewContext().finish();
                }
                break;

            case R.id.iv_call_end:
                if (portSipSdk != null) {
                    portSipSdk.hangUp(mSessionId);
                }
                getViewContext().finish();
                break;

            case R.id.iv_mic_off:
                break;

            case R.id.iv_foward:
                break;

            case R.id.iv_hold:
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
            ivCallAnswer.setImageResource(R.drawable.ic_call_hang_green);
            ivCallCancel.setImageResource(R.drawable.ic_call_end_red);
            ivCallEnd.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
            ivFoward.setVisibility(View.GONE);
            ivHold.setVisibility(View.GONE);
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
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        break;
                    case PortSipService.CALL_EVENT_FAILURE:
                        tvCalling.setText("failure");
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_REJECT:
                        tvCalling.setText("busy");
                        break;
                    case PortSipService.CALL_EVENT_END:
                        tvCalling.setText("ended");
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_ACCEPT:
                        tvCalling.setText("accepted");
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        break;
                    case PortSipService.CALL_EVENT_INVITE_CONNECTED:
                        tvCalling.setText("invite connected");
                        break;
                    default:
                        throw new IllegalArgumentException("error occurred when get event");
                }
            }
        }
    }
}
