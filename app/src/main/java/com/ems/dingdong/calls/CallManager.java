package com.ems.dingdong.calls;

import com.portsip.PortSIPVideoRenderer;
import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipSdk;

public class CallManager {
    private static CallManager mInstance;
    private static Object locker = new Object();
    private Session session;
    public boolean speakerOn = false;
    public boolean regist;
    public boolean online;
    Session[] sessions;


    public boolean setSpeakerOn(PortSipSdk portSipSdk, boolean speakerOn) {
        this.speakerOn = speakerOn;
        if (speakerOn) {
            portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.SPEAKER_PHONE);
        } else {
            portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.EARPIECE);
        }
        return speakerOn;
    }

    public boolean isSpeakerOn() {
        return speakerOn;
    }

    public static CallManager Instance() {
        if (mInstance == null) {
            synchronized (locker) {
                if (mInstance == null) {
                    mInstance = new CallManager();
                }
            }
        }

        return mInstance;
    }

    private CallManager() {
        session = new Session();

    }

    public void hangupAllCalls(PortSipSdk sdk) {
        for (Session session: sessions) {
            if (session.sessionID > Session.INVALID_SESSION_ID) {
                sdk.hangUp(session.sessionID);
            }
        }
    }

    public boolean hasActiveSession() {
        if (session.sessionID > Session.INVALID_SESSION_ID) {
            return true;
        } else
            return false;
    }

    public Session findSessionBySessionID(long SessionID) {
        for(Session session :sessions) {
            if (session.sessionID == SessionID) {
                return session;
            }
        }
        return null;
    }

    public Session findIdleSession() {
        for(Session session :sessions) {
            if (session.IsIdle()) {
                return session;
            }
        }
        return null;
    }

    public void addActiveSessionToConfrence(PortSipSdk sdk)
    {
        for (Session session : sessions)
        {
            if(session.state == Session.CALL_STATE_FLAG.CONNECTED)
            {
                sdk.joinToConference(session.sessionID);
                sdk.sendVideo(session.sessionID, true);
            }
        }
    }

    public void setRemoteVideoWindow(PortSipSdk sdk,long sessionid,PortSIPVideoRenderer renderer){
        sdk.setConferenceVideoWindow(null);
        for (Session session : sessions)
        {
            if(session.state == Session.CALL_STATE_FLAG.CONNECTED&&sessionid!=session.sessionID)
            {
                sdk.setRemoteVideoWindow(session.sessionID,null);
            }
        }
        sdk.setRemoteVideoWindow(sessionid,renderer);
    }

    public void setConferenceVideoWindow(PortSipSdk sdk, PortSIPVideoRenderer renderer){
        for (Session session : sessions)
        {
            if(session.state == Session.CALL_STATE_FLAG.CONNECTED)
            {
                sdk.setRemoteVideoWindow(session.sessionID,null);
            }
        }
        sdk.setConferenceVideoWindow(renderer);
    }
    public void resetAll()
    {
        for(Session session :sessions)
        {
            session.reset();
        }
    }

    public Session findIncomingCall()
    {
        for(Session session :sessions)
        {
            if (session.sessionID != Session.INVALID_SESSION_ID&&session.state== Session.CALL_STATE_FLAG.INCOMING)
            {
                return session;
            }
        }

        return null;
    }

    public Session getSession() {
        return session;
    }

    public void reset() {
        session.reset();
    }
}
