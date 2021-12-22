package com.ems.dingdong.model;

public class Item {
    String value;
    String text;
    String addrest;
    boolean isLienket;
    int img;
    int stt;
    String matin;
    String mabuugui;
    String sohoadon;

    public Item(String value, String text) {
        this.value = value;
        this.text = text;
    }


    public Item(String value, String text, String addrest) {
        this.value = value;
        this.text = text;
        this.addrest = addrest;
    }

    public Item(String value, String text, boolean isLienket, int img) {
        this.value = value;
        this.text = text;
        this.isLienket = isLienket;
        this.img = img;
    }


    public Item(int stt, String matin, String mabuugui, String sohoadon) {
        this.stt = stt;
        this.matin = matin;
        this.mabuugui = mabuugui;
        this.sohoadon = sohoadon;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMatin() {
        return matin;
    }

    public void setMatin(String matin) {
        this.matin = matin;
    }

    public String getMabuugui() {
        return mabuugui;
    }

    public void setMabuugui(String mabuugui) {
        this.mabuugui = mabuugui;
    }

    public String getSohoadon() {
        return sohoadon;
    }

    public void setSohoadon(String sohoadon) {
        this.sohoadon = sohoadon;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isLienket() {
        return isLienket;
    }

    public void setLienket(boolean lienket) {
        isLienket = lienket;
    }

    public String getAddrest() {
        return addrest;
    }

    public void setAddrest(String addrest) {
        this.addrest = addrest;
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
}
