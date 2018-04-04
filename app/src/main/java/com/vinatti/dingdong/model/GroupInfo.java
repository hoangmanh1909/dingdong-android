package com.vinatti.dingdong.model;

import java.util.List;

public class GroupInfo {
    String nameGroup;
    List<HomeInfo> list;

    public GroupInfo(String nameGroup, List<HomeInfo> list) {
        this.nameGroup = nameGroup;
        this.list = list;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public List<HomeInfo> getList() {
        return list;
    }
}
