package com.ems.dingdong.calls;

import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipSdk;

public class CallManager {
    private static CallManager mInstance;
    private static Object locker = new Object();
    private Session session;

    public boolean setSpeakerOn(PortSipSdk portSipSdk, boolean speakerOn) {
        if (speakerOn) {
            portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.SPEAKER_PHONE);
        } else {
            portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.EARPIECE);
        }
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
        if (session.sessionID > Session.INVALID_SESSION_ID) {
            sdk.hangUp(session.sessionID);
        }
    }

    public boolean hasActiveSession() {
        if (session.sessionID > Session.INVALID_SESSION_ID) {
            return true;
        } else
            return false;
    }

    public Session findSessionBySessionID(long SessionID) {
        if (session.sessionID == SessionID) {
            return session;
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
