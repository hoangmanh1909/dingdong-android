package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more;

import com.google.gson.annotations.SerializedName;

public class LadingProduct {
    /// <summary>
    /// Tên sản phẩm
    /// </summary>
    @SerializedName("ProductName")
    private String ProductName;

    /// <summary>
    /// Số lượng
    /// </summary>
    @SerializedName("Quantity")
    private int Quantity;
    /// <summary>
    /// Đơn vị
    /// </summary>
    @SerializedName("UnitName")
    private String UnitName;

    /// <summary>
    /// Khối lượng
    /// </summary>
    @SerializedName("Weight")
    private long Weight;

    /// <summary>
    /// Đơn giá
    /// </summary>
    @SerializedName("Price")
    private long Price;

    /// <summary>
    /// Thành tiền
    /// </summary>
    @SerializedName("Amount")
    private double Amount;

    @SerializedName("Content")
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public long getWeight() {
        return Weight;
    }

    public void setWeight(long weight) {
        Weight = weight;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}
