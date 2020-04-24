package com.ems.dingdong.model;

public class Leaf extends Tree {

    public Leaf(int id, String name) {
        super(id, name);
    }

    @Override
    boolean isLeaf() {
        return false;
    }
}
