package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import java.util.ArrayList;
import java.util.List;

public class CustomListHoanTatNhieuTin {
    private ArrayList<ItemHoanTatNhieuTin> list;
    private int gram;
    private List<String> code;
    private String matin;

    public CustomListHoanTatNhieuTin(ArrayList<ItemHoanTatNhieuTin> list, int gram, List<String> code, String matin) {
        this.list = list;
        this.gram = gram;
        this.code = code;
        this.matin = matin;
    }

    public ArrayList<ItemHoanTatNhieuTin> getList() {
        return list;
    }

    public void setList(ArrayList<ItemHoanTatNhieuTin> list) {
        this.list = list;
    }

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public List<String> getCode() {
        return code;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }

    public String getMatin() {
        return matin;
    }

    public void setMatin(String matin) {
        this.matin = matin;
    }
}
