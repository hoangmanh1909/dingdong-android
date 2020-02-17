package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DingDongCancelDividedRequest {
    @SerializedName("AmndPOCode")
    @Expose
    private String amndPOCode;
    @SerializedName("AmndEmp")
    @Expose
    private Integer amndEmp;
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("FromDeliveryRouteId")
    @Expose
    private Integer fromDeliveryRouteId;
    @SerializedName("FromPostmanId")
    @Expose
    private Integer fromPostmanId;
    @SerializedName("ToDeliveryRouteId")
    @Expose
    private Integer toDeliveryRouteId;
    @SerializedName("ToPostmanId")
    @Expose
    private Integer toPostmanId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("SignatureCapture")
    @Expose
    private String signatureCapture;
    @SerializedName("ImageDelivery")
    @Expose
    private String imageDelivery;

    public String getAmndPOCode() {
        return amndPOCode;
    }

    public void setAmndPOCode(String amndPOCode) {
        this.amndPOCode = amndPOCode;
    }

    public Integer getAmndEmp() {
        return amndEmp;
    }

    public void setAmndEmp(Integer amndEmp) {
        this.amndEmp = amndEmp;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Integer getFromDeliveryRouteId() {
        return fromDeliveryRouteId;
    }

    public void setFromDeliveryRouteId(Integer fromDeliveryRouteId) {
        this.fromDeliveryRouteId = fromDeliveryRouteId;
    }

    public Integer getFromPostmanId() {
        return fromPostmanId;
    }

    public void setFromPostmanId(Integer fromPostmanId) {
        this.fromPostmanId = fromPostmanId;
    }

    public Integer getToDeliveryRouteId() {
        return toDeliveryRouteId;
    }

    public void setToDeliveryRouteId(Integer toDeliveryRouteId) {
        this.toDeliveryRouteId = toDeliveryRouteId;
    }

    public Integer getToPostmanId() {
        return toPostmanId;
    }

    public void setToPostmanId(Integer toPostmanId) {
        this.toPostmanId = toPostmanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignatureCapture() {
        return signatureCapture;
    }

    public void setSignatureCapture(String signatureCapture) {
        this.signatureCapture = signatureCapture;
    }

    public String getImageDelivery() {
        return imageDelivery;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }
}
