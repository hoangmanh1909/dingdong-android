package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.ems.dingdong.model.ItemHoanTatNhieuTin;
import java.util.ArrayList;

public class CustomListHoanTatNhieuTin {
    private ArrayList<ItemHoanTatNhieuTin> list;
    private int gram;

    public CustomListHoanTatNhieuTin(ArrayList<ItemHoanTatNhieuTin> list, int gram) {
        this.list = list;
        this.gram = gram;
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
}
