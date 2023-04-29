package com.ems.dingdong.model.vmapmode;

import com.google.gson.annotations.SerializedName;

public class LoginVmap {

    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
