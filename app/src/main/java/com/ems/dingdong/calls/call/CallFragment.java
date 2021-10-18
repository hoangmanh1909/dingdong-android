package com.ems.dingdong.calls.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.PhoneUpdateCallback;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.PortMessageReceiver;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.diapad.DiapadFragment;
import com.ems.dingdong.dialog.ThongbaoGoiDialog;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
//import com.sip.cmc.SipCmc;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;

public class CallFragment extends ViewFragment<CallContract.Presenter> implements CallContract.View, PortMessageReceiver.BroadcastListener/*, Service*/ {

    public static final String TAG = CallFragment.class.getName();

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
    @BindView(R.id.iv_hold)
    CustomImageView ivHold;
    @BindView(R.id.iv_mic_off)
    CustomImageView ivMicOff;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.tv_sub_info)
    TextView tv_sub_info;
    @BindView(R.id.tv_provider_name)
    CustomBoldTextView tvProviderName;
    private AudioManager audioManager;
    private CallingEvent callingEvent = new CallingEvent();
    private int isSpeak = 10;
    private String receiverNumber = "";
    private String senderNumber = "";
    private String provider = "";
    int apptoapp = 0;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_calling;
    }

    public static CallFragment getInstance() {
        return new CallFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();

        apptoapp = mPresenter.getAppToApp();

        audioManager = (AudioManager) getViewContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        CallManager.Instance().reset();
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            tvPhoneNumber.setText(mPresenter.getCalleeNumber());
            changeCallLayout(Constants.CALL_TYPE_CALLING);
            try {

            } catch (NullPointerException nullPointerException) {

            }
        } else {
            try {
                changeCallLayout(Constants.CALL_TYPE_RECEIVING);
            } catch (NullPointerException nullPointerException) {

            }
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewContext().registerReceiver(callingEvent, new IntentFilter(PortSipService.ACTION_CALL_EVENT));
        Log.d("khiemrwqerwer","aaaa");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.iv_call_end, R.id.iv_hold, R.id.iv_mic_off})
    public void onViewClicked(View view) {
        Session currentLine = CallManager.Instance().getCurrentSession();
        switch (view.getId()) {
            case R.id.iv_call_answer:
                acceptCallCtel();
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    changeCallLayout(Constants.CALL_TYPE_CALLING);
                    mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                } else {
                    new DiapadFragment(getViewContext(), number -> {
                        tvPhoneNumber.setText(number);
                    }).show();
                }
                break;

            case R.id.iv_call_cancel:
                Log.d(TAG, "iv_speaker clicked");
                Log.d(TAG, "mPresenter.getCallType(): " + mPresenter.getCallType());
                if (mPresenter.getCallType() == Constants.CALL_TYPE_RECEIVING) {
                    Ring.getInstance(getViewContext()).stopRingTone();
                    currentLine.reset();
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
                currentLine.reset();
//                SipCmc.hangUp();
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
            case R.id.iv_hold:
                if (isSpeak == 10) {
                    if (!(currentLine.state == Session.CALL_STATE_FLAG.CONNECTED) || currentLine.isHold) {
                        return;
                    }
                    currentLine.isHold = true;
                    ivHold.setImageResource(R.drawable.ic_phone_paused_green);
                    isSpeak++;
                }
                else if (isSpeak == 11) {
                    if (!(currentLine.state == Session.CALL_STATE_FLAG.CONNECTED) || !currentLine.isHold) {
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
            if (apptoapp == 5) {
                tv_info.setText("Gọi qua ứng dụng");
            } else {
                tv_info.setText("Đang thực hiện cuộc gọi");
                tv_sub_info.setText(mPresenter.getCalleeNumber());
            }
            ivHold.setVisibility(View.VISIBLE);
        } else {
            ivCallAnswer.setImageResource(R.drawable.ic_call_hang_green);
            ivCallCancel.setImageResource(R.drawable.ic_call_end_red);
            ivCallEnd.setVisibility(View.INVISIBLE);
            ivMicOff.setVisibility(View.GONE);
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
                        }
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
                        tvCalling.setText("Đang run chuông");
                        break;
                    case PortSipService.CALL_EVENT_TRYING:
                        tvCalling.setText("Đang gọi...");
                        break;
                    case PortSipService.CALL_EVENT_ANSWER:
                        Ring.getInstance(getViewContext()).stopRingTone();
                        break;
                    case PortSipService.CALL_EVENT_FAILURE:
                        tvCalling.setText("Gọi thất bại");
                        chronometer.stop();
                        tvCalling.setVisibility(View.VISIBLE);
                        String tam = "," + mPresenter.getCalleeNumber();
                        new ThongbaoGoiDialog(getViewContext(), mPresenter.getCalleeNumber(), new PhoneUpdateCallback() {
                            @Override
                            public void onCall(String phone) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse(Constants.HEADER_NUMBER + tam));
                                startActivity(intent);
                                finishCall();
                            }
                        }).show();
                        break;
                    case PortSipService.CALL_EVENT_ANSWER_REJECT:
                        tvCalling.setText("Người dùng bận");
                        break;
                    case PortSipService.CALL_EVENT_INVITE_COMMING:
                        changeCallLayout(Constants.CALL_TYPE_RECEIVING);
                        break;
                    case PortSipService.CALL_EVENT_END:
                        try {
                            tvCalling.setText("Kết thúc cuộc gọi");
                            chronometer.stop();
                            tvCalling.setVisibility(View.VISIBLE);
                            chronometer.setVisibility(View.GONE);
                            changeCallLayout(Constants.CALL_TYPE_CALLING);
                            Ring.getInstance(getViewContext()).stopRingTone();
//                            SipCmc.hangUp();
                            finishCall();
                        } catch (NullPointerException nullPointerException) {
                            showErrorToast("Lỗi cuộc gọi");
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
                        break;
                    default:
                        throw new IllegalArgumentException("error occurred when get event");
                }
            }
        }
    }

    private void finishCall() {

            getActivity().finish();
    }

    public enum TypeBD13 {
        CREATE_BD13,
        LIST_BD13
    }

    private void toggleSpeakerCtel() {
        ivCallCancel.setOnClickListener(v -> {
            if (isSpeak == 10) {
//                SipCmc.toggleSpeaker(true);
                ivCallCancel.setImageResource(R.drawable.ic_speaker_green);
                isSpeak++;
            } else if (isSpeak == 11) {
//                SipCmc.toggleSpeaker(false);
                ivCallCancel.setImageResource(R.drawable.ic_button_speaker);
                isSpeak = 10;
            }
        });
    }

    private void acceptCallCtel() {
        try {
//            SipCmc.acceptCall();
        } catch (NullPointerException nullPointerException) {
        }
    }

    private void endCallCtel() {
        try {
//            SipCmc.hangUp();
        } catch (NullPointerException nullPointerException) {
        }
    }


    @Subscribe(sticky = true)
    public void onEvent(CustomItem customItem) {
    }

    @Override
    public void showCallError(String message) {
        if (getViewContext() != null) {
            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Constants.HEADER_NUMBER + "," + mPresenter.getCalleeNumber()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void showCallSuccess() {
        if (getViewContext() != null) {
            if (PermissionUtils.checkToRequest(getViewContext(), CALL_PHONE, REQUEST_CODE_ASK_PERMISSIONS)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Constants.HEADER_NUMBER));
                startActivity(intent);
            }
        }
    }

}
