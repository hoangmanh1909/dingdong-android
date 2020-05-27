package com.ems.dingdong.model.response;

import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.SerializedName;

public class EWalletDataResponse extends SimpleResult {
    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("ReceiverName")
    String receiverName;

    @SerializedName("ReceiverAddress")
    String receiverAddress;

    @SerializedName("CODAmount")
    Integer codAmount;

    @SerializedName("Fee")
    Integer fee;

    boolean isSelected;

    public String getLadingCode() {
        return ladingCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public Integer getCodAmount() {
        return codAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
