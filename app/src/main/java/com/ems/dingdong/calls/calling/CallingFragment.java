package com.ems.dingdong.calls.calling;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.core.base.viper.ViewFragment;
import com.core.utils.PermissionUtils;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.IdCallback;
import com.ems.dingdong.callback.PhoneUpdateCallback;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.PortMessageReceiver;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.diapad.DiapadFragment;
import com.ems.dingdong.dialog.DialoggoiLai;
import com.ems.dingdong.dialog.ThongbaoGoiDialog;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.views.CustomBoldTextView;
import com.ems.dingdong.views.CustomImageView;
import com.ems.dingdong.views.CustomTextView;
import com.sip.cmc.SipCmc;
import com.sip.cmc.callback.PhoneCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.linphone.core.LinphoneCall;


import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class CallingFragment extends ViewFragment<CallingContract.Presenter> implements CallingContract.View, PortMessageReceiver.BroadcastListener/*, Service*/ {

    public static final String TAG = CallingFragment.class.getName();

    @BindView(R.id.iv_call_answer)
    ImageView ivCallAnswer;
    @BindView(R.id.iv_call_cancel)
    ImageView ivCallCancel;

    @BindView(R.id.v_end)
    LinearLayout _llEnd;
    @BindView(R.id.v_speaker)
    LinearLayout _vSpeaker;
    @BindView(R.id.btn_speaker)
    ImageView _btnLoa;
    @BindView(R.id.btn_mute)
    ImageView _btnMic;
    @BindView(R.id.btn_end_call)
    ImageView _btnKetthuc;
    @BindView(R.id.im_network)
    ImageView _imgMang;
    @BindView(R.id.tv_status)
    TextView _tvStatus;
    @BindView(R.id.tv_phone)
    TextView _tvPhone;
    @BindView(R.id.tv_name)
    TextView _tvname;
    @BindView(R.id.v_bottom)
    RelativeLayout _vBottom;
    private long mCallBw = 0;
    private double mPrevCallTimestamp = 0;
    private long mPrevCallBytes = 0;
    private boolean isMute = false;
    private boolean isSpeaker = false;
    private AudioManager audioManager;
    private int isSpeak = 10;
    private String receiverNumber = "";
    private String senderNumber = "";
    private String provider = "";
    int apptoapp = 0;
    private long startTime;
    private TimerTask timerTask;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private String CHANNEL_ID = "VoipChannel";
    private String CHANNEL_NAME = "Voip Channel";
    private static final int SERVICE_NOTIFICATION = 31414;
    public static final int PENDINGCALL_NOTIFICATION = SERVICE_NOTIFICATION + 1;
    private NotificationManager mNotificationManager;
    private String channelID = "PortSipService";
    private String callChannelID = "Call Channel";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_income_call;
    }

    public static CallingFragment getInstance() {
        return new CallingFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            NotificationChannel callChannel = new NotificationChannel(callChannelID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationManager.createNotificationChannel(callChannel);
        }
        apptoapp = mPresenter.getAppToApp();
        SipCmc.toggleSpeaker(isSpeaker);
        CallManager.Instance().reset();
        _tvPhone.setText(mPresenter.getCalleeNumber());
        if (mPresenter.getCallType() == Constants.CALL_TYPE_CALLING) {
            danggoi();
            try {

            } catch (NullPointerException nullPointerException) {

            }
        } else {
            try {
                changeCallLayout(Constants.CALL_TYPE_RECEIVING);
            } catch (NullPointerException nullPointerException) {

            }
        }
        checkNetworkQuality();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("khiemrwqerwer", "aaaa");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void checkNetworkQuality() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        int level = wifiManager.getConnectionInfo().getRssi();
        if (level <= 0 && level >= -50) {
            //Best signal
            _imgMang.setBackgroundResource(R.drawable.exellent);
        } else if (level < -50 && level >= -70) {
            //Good signal
            _imgMang.setBackgroundResource(R.drawable.good);
        } else if (level < -70 && level >= -80) {
            //Low signal
            _imgMang.setBackgroundResource(R.drawable.average);
        } else if (level < -80 && level >= -100) {
            //Very weak signal
            _imgMang.setBackgroundResource(R.drawable.poor);
        } else {
            // no signals
            _imgMang.setBackgroundResource(R.drawable.no_connect);
        }
    }

    @OnClick({R.id.iv_call_cancel, R.id.iv_call_answer, R.id.btn_speaker, R.id.btn_mute, R.id.btn_end_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mute:
                isMute = !isMute;
                if (isMute) {
                    _btnMic.setImageResource(R.drawable.ic_mute_on);
                    SipCmc.toggleMicro(true);
                } else {
                    _btnMic.setImageResource(R.drawable.ic_mute_off);
                    SipCmc.toggleMicro(false);
                }

                break;
            case R.id.btn_speaker:
                isSpeaker = !isSpeaker;
                if (isSpeaker) {
                    SipCmc.toggleSpeaker(true);
                    _btnLoa.setImageResource(R.drawable.ic_speaker_on);
                } else {
                    SipCmc.toggleSpeaker(false);
                    _btnLoa.setImageResource(R.drawable.ic_speaker_off);
                }
                break;
            case R.id.iv_call_answer:
                acceptCallCtel();
                changeCallLayout(Constants.CALL_TYPE_CALLING);
                mPresenter.setCallType(Constants.CALL_TYPE_CALLING);
                danggoi();
                _tvStatus.setText("Cuộc gọi đến");
                break;
            case R.id.iv_call_cancel:
            case R.id.btn_end_call:
                Ring.getInstance(getViewContext()).stopRingTone();
                endCallCtel();
                finishCall();
                break;

        }
    }

    private void danggoi() {
        _tvStatus.setText("Đang gọi...");
        _vSpeaker.setVisibility(View.VISIBLE);
        _llEnd.setVisibility(View.GONE);
        _btnKetthuc.setVisibility(View.VISIBLE);
    }

    private void changeCallLayout(int type) {
        _tvStatus.setText("Cuộc gọi đến");
        _vSpeaker.setVisibility(View.GONE);
        _llEnd.setVisibility(View.VISIBLE);
        _btnKetthuc.setVisibility(View.GONE);
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


    private void finishCall() {
        getActivity().finish();
    }

    public enum TypeBD13 {
        CREATE_BD13,
        LIST_BD13
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
