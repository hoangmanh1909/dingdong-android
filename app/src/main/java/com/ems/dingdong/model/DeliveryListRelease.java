package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryListRelease {
    @SerializedName("ProductId")
    private Long productId;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("Quantity")
    private Integer quantity;
    @SerializedName("UnitName")
    private String unitName;
    @SerializedName("Weight")
    private Long weight;
    @SerializedName("Price")
    private Long price;
    @SerializedName("Amount")
    private Long amounts;
    @SerializedName("LadingToPostmanId")
    private Long ladingToPostmanId;
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("PODeliveryCode")
    private String pODeliveryCode;

    public DeliveryListRelease() {

    }

    public DeliveryListRelease(Long productId, String productName, Integer quantity, String unitName, Long weight, Long price, Long amounts, Long ladingToPostmanId, String ladingCode, String pODeliveryCode) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitName = unitName;
        this.weight = weight;
        this.price = price;
        this.amounts = amounts;
        this.ladingToPostmanId = ladingToPostmanId;
        this.ladingCode = ladingCode;
        this.pODeliveryCode = pODeliveryCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Long getAmounts() {
        return amounts;
    }

    public void setAmounts(Long amounts) {
        this.amounts = amounts;
    }

    public Long getLadingToPostmanId() {
        return ladingToPostmanId;
    }

    public void setLadingToPostmanId(Long ladingToPostmanId) {
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
