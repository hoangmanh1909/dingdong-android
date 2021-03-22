package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

public class CustomItemPartialAdded {

    private String name;
    private int quantity;
    private Long weight;
    private Long price;

    public CustomItemPartialAdded(String name, int quantity, Long weight, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
