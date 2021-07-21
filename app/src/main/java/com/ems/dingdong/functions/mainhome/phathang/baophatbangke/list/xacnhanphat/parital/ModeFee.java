package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

public class ModeFee {

    String tenloaiFee;
    long fee;

    public ModeFee(String tenloaiFee, long fee) {
        this.tenloaiFee = tenloaiFee;
        this.fee = fee;
    }

    public String getTenloaiFee() {
        return tenloaiFee;
    }

    public void setTenloaiFee(String tenloaiFee) {
        this.tenloaiFee = tenloaiFee;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }
}
