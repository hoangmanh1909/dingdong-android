package com.ems.dingdong.model.response;

import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DingDongGetCancelDelivery;
import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DingDongGetCancelDeliveryResponse extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<DingDongGetCancelDelivery> deliveryPostmens;

    public void setDeliveryPostmens(ArrayList<DingDongGetCancelDelivery> deliveryPostmens) {
        this.deliveryPostmens = deliveryPostmens;
    }

    public ArrayList<DingDongGetCancelDelivery> getDeliveryPostmens() {
        return deliveryPostmens;
    }
}
