package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemV1 {
    String value;
    String text;
    int i;
    boolean is;
    String color;
    int soluong;
    String tungay;
    String denngay;
    String bienthe;

    public ItemV1(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public ItemV1(String value, String bienthe, String text) {
        this.value = value;
        this.bienthe = bienthe;
        this.text = text;
    }


    public ItemV1(String value, String text, String tungay, String denngay, int i) {
        this.value = value;
        this.text = text;
        this.tungay = tungay;
        this.denngay = denngay;
        this.i = i;
    }

    public ItemV1(String value, String text, boolean is) {
        this.value = value;
        this.text = text;
        this.is = is;
    }

    public ItemV1(String color, String text, int soluong) {
        this.color = color;
        this.text = text;
        this.soluong = soluong;
    }

    public String getBienthe() {
        return bienthe;
    }

    public void setBienthe(String bienthe) {
        this.bienthe = bienthe;
    }

    public String getTungay() {
        return tungay;
    }

    public void setTungay(String tungay) {
        this.tungay = tungay;
    }

    public String getDenngay() {
        return denngay;
    }

    public void setDenngay(String denngay) {
        this.denngay = denngay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Code")
    @Expose
    private String Code;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCode(String code) {
        Code = code;
    }
}
