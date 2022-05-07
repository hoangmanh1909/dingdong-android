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
    String sotaikhoan;
    int lv1;
    int lv2;
    String tvlv1;
    String tvlv2;

    public Item(String value, String text) {
        this.value = value;
        this.text = text;
    }


    public Item(String value, String text, String addrest) {
        this.value = value;
        this.text = text;
        this.addrest = addrest;
    }

    public Item(String value, String text, boolean isLienket, int img, String sotaikhoan) {
        this.value = value;
        this.text = text;
        this.isLienket = isLienket;
        this.img = img;
        this.sotaikhoan = sotaikhoan;
    }

    public String getSotaikhoan() {
        return sotaikhoan;
    }

    public void setSotaikhoan(String sotaikhoan) {
        this.sotaikhoan = sotaikhoan;
    }

    public Item(int stt, String matin, String mabuugui, String sohoadon) {
        this.stt = stt;
        this.matin = matin;
        this.mabuugui = mabuugui;
        this.sohoadon = sohoadon;
    }
    public Item(int lv1, int lv2,String value) {
        this.value = value;
        this.lv1 = lv1;
        this.lv2 = lv2;

    }


    public int getLv1() {
        return lv1;
    }

    public void setLv1(int lv1) {
        this.lv1 = lv1;
    }

    public int getLv2() {
        return lv2;
    }

    public void setLv2(int lv2) {
        this.lv2 = lv2;
    }

    public String getTvlv1() {
        return tvlv1;
    }

    public void setTvlv1(String tvlv1) {
        this.tvlv1 = tvlv1;
    }

    public String getTvlv2() {
        return tvlv2;
    }

    public void setTvlv2(String tvlv2) {
        this.tvlv2 = tvlv2;
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
