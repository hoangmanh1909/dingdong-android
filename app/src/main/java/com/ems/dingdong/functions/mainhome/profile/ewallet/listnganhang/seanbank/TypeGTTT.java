package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

public class TypeGTTT {
    int swap(String gttt) {
        int type = 1;
        switch (gttt) {
            case "CMND":
                type = 1;
                break;
            case "TCC":
                type = 2;
                break;
            case "PP":
                type = 3;
                break;
        }
        return type;
    }

}
