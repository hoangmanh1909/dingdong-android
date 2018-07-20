package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bd13Create {
    @SerializedName("ListCode")
    @Expose
    private List<Bd13Code> listCode = null;
    @SerializedName("RoutePOCode")
    @Expose
    private String routePOCode;
    @SerializedName("DeliveryPOCode")
    @Expose
    private String deliveryPOCode;
    @SerializedName("BagNumber")
    @Expose
    private String bagNumber;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("ChuyenThu")
    @Expose
    private String chuyenThu;
    @SerializedName("Shift")
    @Expose
    private String shift;

    public void setListCode(List<Bd13Code> listCode) {
        this.listCode = listCode;
    }

    public void setRoutePOCode(String routePOCode) {
        this.routePOCode = routePOCode;
    }

    public void setDeliveryPOCode(String deliveryPOCode) {
        this.deliveryPOCode = deliveryPOCode;
    }

    public void setBagNumber(String bagNumber) {
        this.bagNumber = bagNumber;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setChuyenThu(String chuyenThu) {
        this.chuyenThu = chuyenThu;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
