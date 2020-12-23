package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

public class CustomAddress {
    int parcelCode;
    String address;
    String phoneNumber;
    String name;

    public CustomAddress(int parcelCode, String address, String phoneNumber, String name) {
        this.parcelCode = parcelCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public int getParcelCode() {
        return parcelCode;
    }

    public void setParcelCode(int parcelCode) {
        this.parcelCode = parcelCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
