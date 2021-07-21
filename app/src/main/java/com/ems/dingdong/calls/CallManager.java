package com.ems.dingdong.calls;


public class CallManager {
    private static CallManager mInstance;
    private static Object locker = new Object();
    private Session session;
    public boolean speakerOn = false;
    public boolean regist;
    public boolean online;
    Session[] sessions;
    public int CurrentLine;
    public static final int MAX_LINES = 10;

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
        CurrentLine = 0;
        sessions = new Session[MAX_LINES];
        for (int i = 0; i < sessions.length; i++)
        {
            sessions[i] = new Session();
            sessions[i].lineName = "line - " + i;

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

    public void resetAll()
    {
        for(Session session :sessions)
        {
            session.reset();
        }
    }

    public Session findIncomingCall() {
        for(Session session :sessions) {
            if (session.sessionID != Session.INVALID_SESSION_ID&&session.state== Session.CALL_STATE_FLAG.INCOMING) {
                return session;
            }
        }

        return null;
    }

    public Session getSession() {
        return session;
    }

    public void reset() {
        try {
            session.reset();
        }catch (NullPointerException nullPointerException){

        }
    }

    public Session getCurrentSession()
    {
        if (CurrentLine >= 0 && CurrentLine <= sessions.length)
        {
            return sessions[CurrentLine];

        }
        return null;
    }
}
