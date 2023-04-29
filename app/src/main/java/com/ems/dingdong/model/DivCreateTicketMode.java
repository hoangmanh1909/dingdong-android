package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DivCreateTicketMode {
    /// <summary>
    /// Id bưu tá login
    /// </summary>
    @SerializedName("PostmanId")
    public long PostmanId;

    /// <summary>
    /// Dánh sách bg tạo ticket - nối - dấu ; A1;A2;A3...
    /// </summary>
    @SerializedName("LadingCode")
    public String LadingCode;
    /// <summary>
    /// Mã bc phát của bưu tá login
    /// </summary>
    @SerializedName("PODeliveryCode")
    public String PODeliveryCode;
    /// <summary>
    /// Chi tiết yêu cầu
    /// </summary>
    @SerializedName("Description")
    public String Description;
    /// <summary>
    /// Mã nội dung yêu cầu
    /// Gọi hàm
    /// </summary>
    @SerializedName("SubSolutionCode")
    public String SubSolutionCode;
    /// <summary>
    /// Chiều dài
    /// </summary>
    @SerializedName("Length")
    public long Length;
    /// <summary>
    /// Chiều rộng
    /// </summary>
    @SerializedName("Width")
    public long Width;
    /// <summary>
    /// Chiều cao
    /// </summary>
    @SerializedName("Height")
    public long Height;
    /// <summary>
    /// Phí chênh lệch
    /// </summary>
    @SerializedName("Fee")
    public long Fee;
    /// <summary>
    /// Link ảnh đính kèm
    /// </summary>
    @SerializedName("ImagePath")
    public String ImagePath;
    @SerializedName("SolutionCode")
    public String SolutionCode;

    public String getSolutionCode() {
        return SolutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        SolutionCode = solutionCode;
    }

    public long getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(long postmanId) {
        PostmanId = postmanId;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getPODeliveryCode() {
        return PODeliveryCode;
    }

    public void setPODeliveryCode(String PODeliveryCode) {
        this.PODeliveryCode = PODeliveryCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubSolutionCode() {
        return SubSolutionCode;
    }

    public void setSubSolutionCode(String subSolutionCode) {
        SubSolutionCode = subSolutionCode;
    }

    public long getLength() {
        return Length;
    }

    public void setLength(long length) {
        Length = length;
    }

    public long getWidth() {
        return Width;
    }

    public void setWidth(long width) {
        Width = width;
    }

    public long getHeight() {
        return Height;
    }

    public void setHeight(long height) {
        Height = height;
    }

    public long getFee() {
        return Fee;
    }

    public void setFee(long fee) {
        Fee = fee;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
