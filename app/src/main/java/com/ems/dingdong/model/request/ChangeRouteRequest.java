package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeRouteRequest {
    @SerializedName("POCode")
    @Expose
    private String poCode;
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("FromRouteId")
    @Expose
    private Integer fromRouteId;
    @SerializedName("PostmanId")
    @Expose
    private Integer postmanId;
    @SerializedName("ToRouteId")
    @Expose
    private Integer toRouteId;
    @SerializedName("ToPostmanId")
    @Expose
    private Integer toPostmanId;

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Integer getFromRouteId() {
        return fromRouteId;
    }

    public void setFromRouteId(Integer fromRouteId) {
        this.fromRouteId = fromRouteId;
    }

    public Integer getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(Integer postmanId) {
        this.postmanId = postmanId;
    }

    public Integer getToRouteId() {
        return toRouteId;
    }

    public void setToRouteId(Integer toRouteId) {
        this.toRouteId = toRouteId;
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

    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("SignatureCapture")
    @Expose
    private String signatureCapture;
    @SerializedName("ImagePath")
    @Expose
    private String imageDelivery;
}
