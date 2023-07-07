package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPostageRespone {
    /// Số tiền cước đã thu
    @SerializedName("PostageAmount")
    @Expose
    private long PostageAmount  ;

    /// Số tiền cước COD đã thu
    @SerializedName("PostageCODAmount")
    @Expose
    private long PostageCODAmount  ;

    public long getPostageAmount() {
        return PostageAmount;
    }

    public void setPostageAmount(long postageAmount) {
        PostageAmount = postageAmount;
    }

    public long getPostageCODAmount() {
        return PostageCODAmount;
    }

    public void setPostageCODAmount(long postageCODAmount) {
        PostageCODAmount = postageCODAmount;
    }
}
