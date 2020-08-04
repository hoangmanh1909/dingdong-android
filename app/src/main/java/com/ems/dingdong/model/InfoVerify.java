package com.ems.dingdong.model;

public class InfoVerify {
    private String receiverBirthday;

    private String receiverPIDDate;

    private String receiverPIDWhere;

    private String receiverAddressDetail;

    private Integer authenType;

    private String gtgt;

    public void setReceiverBirthday(String receiverBirthday) {
        this.receiverBirthday = receiverBirthday;
    }

    public void setReceiverPIDDate(String receiverPIDDate) {
        this.receiverPIDDate = receiverPIDDate;
    }

    public void setReceiverPIDWhere(String receiverPIDWhere) {
        this.receiverPIDWhere = receiverPIDWhere;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
    }

    public String getReceiverBirthday() {
        return receiverBirthday;
    }

    public String getReceiverPIDDate() {
        return receiverPIDDate;
    }

    public String getReceiverPIDWhere() {
        return receiverPIDWhere;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public Integer getAuthenType() {
        return authenType;
    }

    public String getGtgt() {
        return gtgt;
    }

    public void setGtgt(String gtgt) {
        this.gtgt = gtgt;
    }
}
