package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("ProductId")
    private long productId;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("UnitName")
    private String unitName;
    @SerializedName("Weight")
    private long weight;
    @SerializedName("Price")
    private long price;
    @SerializedName("Amount")
    private long amounts;
    @SerializedName("LadingToPostmanId")
    private long ladingToPostmanId;
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("PODeliveryCode")
    private String pODeliveryCode;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getAmounts() {
        return amounts;
    }

    public void setAmounts(long amounts) {
        this.amounts = amounts;
    }

    public long getLadingToPostmanId() {
        return ladingToPostmanId;
    }

    public void setLadingToPostmanId(long ladingToPostmanId) {
        this.ladingToPostmanId = ladingToPostmanId;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public String getpODeliveryCode() {
        return pODeliveryCode;
    }

    public void setpODeliveryCode(String pODeliveryCode) {
        this.pODeliveryCode = pODeliveryCode;
    }
}
