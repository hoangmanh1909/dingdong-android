package com.ems.dingdong.calls.answer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.calling.CallingFragment;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.views.CustomImageView;
import com.portsip.PortSipErrorcode;
import com.portsip.PortSipSdk;

import butterknife.BindView;

public class AnswerActivity extends AppCompatActivity implements PortMessageReceiver.BroadcastListener, View.OnClickListener {
    public PortMessageReceiver receiver = null;
    ApplicationController application;
    Button btnVideo, btnHangup;
    @BindView(R.id.tv_phone_call)
    TextView tvPhoneCall;
    long mSessionid;
    String mPhoneCaller = "";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


        final Window win = getWindow();
        win.addFlags( WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        btnVideo = findViewById(R.id.answer_video);
        receiver = new PortMessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PortSipService.REGISTER_CHANGE_ACTION);
        filter.addAction(PortSipService.CALL_CHANGE_ACTION);
        filter.addAction(PortSipService.PRESENCE_CHANGE_ACTION);
        registerReceiver(receiver, filter);
        receiver.broadcastReceiver =this;
        Intent intent = getIntent();

        findViewById(R.id.btn_hangup).setOnClickListener(this);
        findViewById(R.id.btn_answer).setOnClickListener(this);
        btnVideo.setOnClickListener(this);

        mSessionid = intent.getLongExtra(PortSipService.EXTRA_CALL_SEESIONID, PortSipErrorcode.INVALID_SESSION_ID);

        try {
            mPhoneCaller = intent.getStringExtra(PortSipService.EXTRA_PHONE_CALLER);
            Log.d("123123", "phone 123: "+mPhoneCaller);
            Toast.makeText(application, ""+ mPhoneCaller, Toast.LENGTH_SHORT).show();
            if (mPhoneCaller != null)
            tvPhoneCall.setText(mPhoneCaller);
        }catch (NullPointerException nullPointerException){}

        Log.d("123123", "mSessionid activity: "+ mSessionid);
        Session session = CallManager.Instance().findSessionBySessionID(mSessionid);
        if(mSessionid==PortSipErrorcode.INVALID_SESSION_ID||session ==null||session.state!= Session.CALL_STATE_FLAG.INCOMING){
            this.finish();
            return;
        }

        application = (ApplicationController) getApplication();
        setVideoAnswerVisibility(session);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        long sessionid = intent.getLongExtra("incomingSession", PortSipErrorcode.INVALID_SESSION_ID);
        Session session = CallManager.Instance().findSessionBySessionID(sessionid);
        if(mSessionid!=PortSipErrorcode.INVALID_SESSION_ID&&session !=null){
            mSessionid = sessionid;
            setVideoAnswerVisibility(session);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        this.finish();
    }

    @Override
    public void onBroadcastReceiver(Intent intent) {
        String action = intent.getAction();
        if (PortSipService.CALL_CHANGE_ACTION.equals(action)) {
            long sessionId = intent.getLongExtra(PortSipService.EXTRA_CALL_SEESIONID, Session.INVALID_SESSION_ID);
            String status = intent.getStringExtra(PortSipService.EXTRA_CALL_DESCRIPTION);
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
                            this.finish();
                        } else {
                            mSessionid = anOthersession.sessionID;
                        }
                        break;

                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        if(application.portSipSdk!=null){
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(PortSipService.PENDINGCALL_NOTIFICATION);
            Session currentLine = CallManager.Instance().findSessionBySessionID(mSessionid);
            Log.d("123123", "currentLine: "+currentLine);
            switch (view.getId()){
                case R.id.btn_answer:
                    //Toast.makeText(application, "click", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putLong("sessionId", mSessionid);
                    // set Fragmentclass Arguments
                    CallingFragment fragobj = new CallingFragment();
                    fragobj.setArguments(bundle);

                    Intent intentAnswer = new Intent(this, IncomingCallActivity.class);
                    startActivity(intentAnswer);
                    finish();
                case R.id.answer_video:
                    if (currentLine.state != Session.CALL_STATE_FLAG.INCOMING) {
                        Toast.makeText(this,currentLine.lineName + "No incoming call on current line",Toast.LENGTH_SHORT);
                        try {
                            btnHangup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    application.portSipSdk.rejectCall(currentLine.sessionID, 486);
                                    currentLine.reset();
                                }
                            });
                        }catch (NullPointerException nullPointerException){}

                        return;
                    }
                    Ring.getInstance(this).stopRingTone();
                    currentLine.state = Session.CALL_STATE_FLAG.CONNECTED;
                    application.portSipSdk.answerCall(mSessionid,view.getId()==R.id.answer_video);
                    if(application.mConference){
                        application.portSipSdk.joinToConference(currentLine.sessionID);
                        Log.d("123123", "currentLine.sessionID: "+currentLine.sessionID);
                    }
                    break;
                case R.id.btn_hangup:
                    Ring.getInstance(this).stop();
                    if (currentLine.state == Session.CALL_STATE_FLAG.INCOMING) {
                        application.portSipSdk.rejectCall(currentLine.sessionID, 486);
                        currentLine.reset();
                        Toast.makeText(this,currentLine.lineName + ": Rejected call",Toast.LENGTH_SHORT);
                    }
                    this.finish();///

                    break;
            }
        }

        /*Session anOthersession = CallManager.Instance().findIncomingCall();
        if(anOthersession==null) {
            //this.finish();
            Intent intentAnswer = new Intent(this, IncomingCallActivity.class);
            startActivity(intentAnswer);
            Log.d("123123", "intent 2");
            this.finish();
        }else{
            mSessionid = anOthersession.sessionID;
            setVideoAnswerVisibility(anOthersession);
        }*/

    }

    private void setVideoAnswerVisibility(Session session){
        if(session == null)
            return;
        if(session.hasVideo){
            btnVideo.setVisibility(View.VISIBLE);
        }else{
            btnVideo.setVisibility(View.GONE);
        }
    }
}