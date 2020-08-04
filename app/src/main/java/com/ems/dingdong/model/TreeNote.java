package com.ems.dingdong.model;

import java.util.List;

public class TreeNote extends Tree {

    private List<Tree> mListChild;

    public TreeNote(int id, String name, List<Tree> listChild) {
        super(id, name);
        mListChild = listChild;
    }

    @Override
    boolean isLeaf() {
        return false;
    }

    public List<Tree> getListChild() {
        return mListChild;
    }

    public void setListChild(List<Tree> listChild) {
        this.mListChild = listChild;
    }

    public void addChild(Tree tree) {
        mListChild.add(tree);
    }
}
