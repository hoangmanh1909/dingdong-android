package com.ems.dingdong.model;

public class Item {
    String  value;
    String text;
    String addrest;
    public Item(String value, String text) {
        this.value = value;
        this.text = text;
    }


    public Item(String value, String text, String addrest) {
        this.value = value;
        this.text = text;
        this.addrest = addrest;
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
