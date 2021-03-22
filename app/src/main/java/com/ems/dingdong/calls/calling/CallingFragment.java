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
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.portsip.PortSipSdk;
import com.sip.cmc.SipCmc;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.OnClick;

public class CallingFragment extends ViewFragment<CallingContract.Presenter> implements CallingContract.View, PortMessageReceiver.BroadcastListener/*, Service*/ {

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
//    @BindView(R.id.iv_foward)
//    CustomImageView ivFoward;
    @BindView(R.id.iv_hold)
    CustomImageView ivHold;
    @BindView(R.id.iv_mic_off)
    CustomImageView ivMicOff;
    @BindView(R.id.tv_provider_name)
    CustomBoldTextView tvProviderName;
    private AudioManager audioManager;
    private PortSipSdk portSipSdk;
    private Session session;
    private CallingEvent callingEvent = new CallingEvent();
    private long mSessionId;
    private String xSessionIdPostmanOut = "";
    private UserInfo userInfo;
    private PostOffice postOffice;
    private RouteInfo routeInfo;
    private TypeBD13 mTypeBD13;
    private String ladingCode = "";
    private int isSpeak = 10;
    private String receiverNumber = "";
    private String senderNumber = "";
    private String provider = "";


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

        SharedPref sharedPref = new SharedPref(getActivity());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

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
        //Tạm thời comment
        getViewContext().registerReceiver(callingEvent, new IntentFilter(PortSipService.ACTION_CALL_EVENT));
    }

    @Override
    public void onDestroy() {
        /*if (callingEvent != null)
            getViewContext().unregisterReceiver(callingEvent);*/
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    /*@Override
    public IBinder onBind(Intent intent) {
        return null;
    }*/
//, R.id.iv_foward
    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.iv_call_end, R.id.iv_hold, R.id.iv_mic_off})
    public void onViewClicked(View view) {

        Session currentLine = CallManager.Instance().getCurrentSession();

        switch (view.getId()) {
            case R.id.iv_call_answer:
                //Ctel
                acceptCallCtel();

                //createHistoryVHTIn();
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
                createHistoryVHTIn();

                Log.d(TAG, "iv_speaker clicked");
                Log.d(TAG, "mPresenter.getCallType(): "+mPresenter.getCallType());
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    //createHistoryVHTIn();
                    portSipSdk.rejectCall(session.sessionID, 486);
                    portSipSdk.hangUp(session.sessionID);
                    Ring.getInstance(getViewContext()).stopRingTone();
                    currentLine.reset();
                    ///getViewContext().finish();
                    endCallCtel();
                    finishCall();
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
                        //createHistoryVHTIn();
                        portSipSdk.hangUp(currentLine.sessionID);
                        portSipSdk.rejectCall(currentLine.sessionID, 486);
                    } catch (NullPointerException nullPointerException) {
                    }
                }

                currentLine.reset();
                ///getViewContext().finish();

                //ctel
                SipCmc.hangUp();
                finishCall();
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

//            case R.id.iv_foward:
//                break;

            case R.id.iv_hold:
                if (isSpeak == 10) {
                    if (!(currentLine.state == Session.CALL_STATE_FLAG.CONNECTED) || currentLine.isHold) {
                        return;
                    }
                    int result = portSipSdk.hold(currentLine.sessionID);
                    if (result != 10) {
                        return;
                    }
                    currentLine.isHold = true;
                    ivHold.setImageResource(R.drawable.ic_phone_paused_green);
                    isSpeak++;
                } else if (isSpeak == 11) {
                    if (!(currentLine.state == Session.CALL_STATE_FLAG.CONNECTED) || !currentLine.isHold) {
                        return;
                    }
                    int result = portSipSdk.unHold(currentLine.sessionID);
                    if (result != 10) {
                        currentLine.isHold = false;
                        return;
                    }
                    currentLine.isHold = false;
                    ivHold.setImageResource(R.drawable.ic_phone_paused);
                    isSpeak = 10;
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
//            ivFoward.setVisibility(View.GONE);//VISIBLE
            ivHold.setVisibility(View.VISIBLE);
        } else {
            tvPhoneNumber.setText(session.displayName);///
            ivCallAnswer.setImageResource(R.drawable.ic_call_hang_green);
            ivCallCancel.setImageResource(R.drawable.ic_call_end_red);
            ivCallEnd.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
//            ivFoward.setVisibility(View.GONE);
            ivHold.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBroadcastReceiver(Intent intent) {
        String action = intent.getAction();
        if (PortSipService.CALL_CHANGE_ACTION.equals(action)) {
            long sessionId = intent.getLongExtra(PortSipService.EXTRA_CALL_SEESIONID, Session.INVALID_SESSION_ID);
            Session session = CallManager.Instance().findSessionBySessionID(sessionId);
            if (session != null) {
                switch (session.state) {
                    case INCOMING:
                        break;
                    case TRYING:
                        break;
                    case CONNECTED:
                    case FAILED:
                    case CLOSED:
                        Session anOthersession = CallManager.Instance().findIncomingCall();
                        if (anOthersession == null) {
                            finishCall();
                        }/*else{
                            sessionId = anOthersession.sessionID;
                        }*/
                        break;
                    case INVITESESSION_PROGRESS:
                        Session anOthersession1 = CallManager.Instance().findIncomingCall();
                        if (anOthersession1 != null) {

                        }
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
                        finishCall();
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_REJECT:
                        tvCalling.setText("busy");
                        break;
                    case PortSipService.CALL_EVENT_INVITE_COMMING:
                        //tvCalling.setText(session.phoneNumber);
                        changeCallLayout(Constants.CALL_TYPE_RECEIVING);
                        createHistoryVHTIn();
                        break;
                    case PortSipService.CALL_EVENT_END:
                        try {
                            tvCalling.setText("call ended");
                            chronometer.stop();
                            tvCalling.setVisibility(View.VISIBLE);
                            chronometer.setVisibility(View.GONE);
                            changeCallLayout(Constants.CALL_TYPE_CALLING);
                            Ring.getInstance(getViewContext()).stopRingTone();
                            createHistoryVHTIn();
                            //SipCmc.hangUp();
                            finishCall();
                        } catch (NullPointerException nullPointerException) {
                            showErrorToast("error cal end");
                        }
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_ACCEPT:
                        tvCalling.setText("accepted");
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        toggleSpeakerCtel();
                        break;
                    case PortSipService.CALL_EVENT_INVITE_CONNECTED:
                        chronometer.setVisibility(View.VISIBLE);
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        tvCalling.setVisibility(View.GONE);
                        toggleSpeakerCtel();
                        Ring.getInstance(getViewContext()).stopRingTone();
                        break;
                    case PortSipService.CALL_EVENT_SESSION_PROGRESS:
                        createHistoryVHTOut();
                        break;
                    default:
                        throw new IllegalArgumentException("error occurred when get event");
                }
            }
        }
    }

    private void finishCall() {
        try {
            getActivity().finish();
        }catch (Exception e) {}

        xSessionIdPostmanOut = "";
        ladingCode = "";
    }

    private void createHistoryVHTOut() {
        mPresenter.createCallHistoryVHTOut();
    }

    public void createHistoryVHTIn() {
        mPresenter.createCallHistoryVHTIn();
    }

    public enum TypeBD13 {
        CREATE_BD13,
        LIST_BD13
    }

    private void toggleSpeakerCtel() {
        ivCallCancel.setOnClickListener(v -> {
            if (isSpeak == 10) {
                SipCmc.toggleSpeaker(true);
                ivCallCancel.setImageResource(R.drawable.ic_speaker_green);
                isSpeak++;
            } else if (isSpeak == 11) {
                SipCmc.toggleSpeaker(false);
                ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                isSpeak = 10;
            }
        });
    }

    private void acceptCallCtel() {
        try {
            SipCmc.acceptCall();
        } catch (NullPointerException nullPointerException) {
        }
    }

    private void endCallCtel() {
        try {
            SipCmc.hangUp();
        }catch (NullPointerException nullPointerException) {}
    }

    private void transferCallCtel() {
        //SipCmc.transferCall();
    }


    @Subscribe(sticky = true)
    public void onEvent(CustomItem customItem) {
        xSessionIdPostmanOut = customItem.getMessage();
    }
}
