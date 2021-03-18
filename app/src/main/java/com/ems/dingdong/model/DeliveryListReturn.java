package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryListReturn {
    @SerializedName("ProductId")
    private Long productId;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("Quantity")
    private Integer quantity;
    @SerializedName("UnitName")
    private String unitName;
    @SerializedName("Weight")
    private Long Weight;
    @SerializedName("Price")
    private Long price;
    @SerializedName("Amount")
    private Long amounts;
    @SerializedName("LadingToPostmanId")
    private Long LadingToPostmanId;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("PODeliveryCode")
    private String pODeliveryCode;

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
        return Weight;
    }

    public void setWeight(Long weight) {
        Weight = weight;
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
        return LadingToPostmanId;
    }

    public void setLadingToPostmanId(Long ladingToPostmanId) {
        LadingToPostmanId = ladingToPostmanId;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getpODeliveryCode() {
        return pODeliveryCode;
    }

    public void setpODeliveryCode(String pODeliveryCode) {
        this.pODeliveryCode = pODeliveryCode;
    }
}
