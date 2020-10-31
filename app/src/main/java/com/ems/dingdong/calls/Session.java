package com.ems.dingdong.calls;

public class Session {
    public static int INVALID_SESSION_ID = -1;
    public long sessionID;
    public String remote;
    public String displayName;
    public String phoneNumber;

    public boolean hasVideo;
    public boolean isHold = false;
    public boolean bMute;
    public boolean bEarlyMedia;
    public boolean isMicOff = true;
    public boolean isSpeakerOff = true;
    public String lineName;
    public CALL_STATE_FLAG state;

    public boolean IsIdle()
    {
        return state == CALL_STATE_FLAG.FAILED || state == CALL_STATE_FLAG.CLOSED;
    }
    public Session()
    {
        remote = null;
        displayName = null;
        hasVideo = false;
        sessionID = INVALID_SESSION_ID;
        state = CALL_STATE_FLAG.CLOSED;
    }

    public void reset()
    {
        remote = null;
        displayName = null;
        hasVideo = false;
        state = CALL_STATE_FLAG.CLOSED;
        bEarlyMedia = false;
        isMicOff = false;
        isHold = false;
    }

    public enum CALL_STATE_FLAG
    {
        INCOMING,
        TRYING ,
        CONNECTED,
        FAILED,
        CLOSED,
    }
}
