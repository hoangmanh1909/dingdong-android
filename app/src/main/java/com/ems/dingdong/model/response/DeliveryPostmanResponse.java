package com.ems.dingdong.model.response;

import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeliveryPostmanResponse extends SimpleResult {

    @SerializedName("ListValue")
    private ArrayList<DeliveryPostman> deliveryPostmens;

    public void setDeliveryPostmens(ArrayList<DeliveryPostman> deliveryPostmens) {
        this.deliveryPostmens = deliveryPostmens;
    }

    public ArrayList<DeliveryPostman> getDeliveryPostmens() {
        return deliveryPostmens;
    }

}
