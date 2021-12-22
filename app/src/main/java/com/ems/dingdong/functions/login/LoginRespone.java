package com.ems.dingdong.functions.login;

import com.google.gson.annotations.SerializedName;

public class LoginRespone {
    @SerializedName("Version")
    String Version;
    @SerializedName("UrlDownload")
    String UrlDownload;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getUrlDownload() {
        return UrlDownload;
    }

    public void setUrlDownload(String urlDownload) {
        UrlDownload = urlDownload;
    }
}
