package com.ems.dingdong.model;

import java.util.List;

public class Item {
    String value;
    String text;
    String addrest;
    boolean isLienket;
    String img;
    int stt;
    String matin;
    String mabuugui;
    String sohoadon;
    String sotaikhoan;
    int lv1;
    int lv2;
    String tvlv1;
    String tvlv2;
    String BankCode;
    String PIDNumber;
    String PIDType;
    String POCode;
    String PaymentToken;
    boolean defaut;
    boolean is;
    List<CommonObject> list;
    List<SolutionInfo> solutionInfos;

    public Item(String value, String text) {
        this.value = value;
        this.text = text;
    }


    public Item(String value, String text, List<SolutionInfo> solutionInfos) {
        this.value = value;
        this.text = text;
        this.solutionInfos = solutionInfos;
    }

    public Item(String value, String text, List<CommonObject> list, boolean is) {
        this.value = value;
        this.text = text;
        this.is = is;
        this.list = list;
    }


    public Item(String value, String text, String addrest) {
        this.value = value;
        this.text = text;
        this.addrest = addrest;
    }

    public Item(String value, String text, boolean isLienket, String img, String sotaikhoan, boolean defaut) {
        this.value = value;
        this.text = text;
        this.isLienket = isLienket;
        this.img = img;
        this.sotaikhoan = sotaikhoan;
        this.defaut = defaut;
    }

    public Item(String value, String text, boolean isLienket, String img, String sotaikhoan, String BankCode,
                String PIDNumber, String PIDType, String POCode, String PaymentToken, boolean defaut) {
        this.value = value;
        this.text = text;
        this.isLienket = isLienket;
        this.img = img;
        this.sotaikhoan = sotaikhoan;
        this.BankCode = BankCode;
        this.PIDNumber = PIDNumber;
        this.PIDType = PIDType;
        this.POCode = POCode;
        this.PaymentToken = PaymentToken;
        this.defaut = defaut;
    }

    public List<SolutionInfo> getSolutionInfos() {
        return solutionInfos;
    }

    public void setSolutionInfos(List<SolutionInfo> solutionInfos) {
        this.solutionInfos = solutionInfos;
    }

    public List<CommonObject> getList() {
        return list;
    }

    public void setList(List<CommonObject> list) {
        this.list = list;
    }

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

    public boolean isDefaut() {
        return defaut;
    }

    public void setDefaut(boolean defaut) {
        this.defaut = defaut;
    }

    public String getPaymentToken() {
        return PaymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        PaymentToken = paymentToken;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getPIDNumber() {
        return PIDNumber;
    }

    public void setPIDNumber(String PIDNumber) {
        this.PIDNumber = PIDNumber;
    }

    public String getPIDType() {
        return PIDType;
    }

    public void setPIDType(String PIDType) {
        this.PIDType = PIDType;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
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

    public Item(int lv1, int lv2, String value) {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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
