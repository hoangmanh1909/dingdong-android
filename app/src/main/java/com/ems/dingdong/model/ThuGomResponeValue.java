package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ThuGomResponeValue {
    @SerializedName("TotalAddressCollect")
    public int TotalAddressCollect;
    @SerializedName("TotalLadingCollect")
    public int TotalLadingCollect;
    @SerializedName("TotalWeightCollect")
    public int TotalWeightCollect;
    @SerializedName("TotalAddressNotCollect")
    public int TotalAddressNotCollect;
    @SerializedName("TotalLadingNotCollect")
    public int TotalLadingNotCollect;
    @SerializedName("TotalWeightNotCollect")
    public int TotalWeightNotCollect;

    public int getTotalAddressCollect() {
        return TotalAddressCollect;
    }

    public void setTotalAddressCollect(int totalAddressCollect) {
        TotalAddressCollect = totalAddressCollect;
    }

    public int getTotalLadingCollect() {
        return TotalLadingCollect;
    }

    public void setTotalLadingCollect(int totalLadingCollect) {
        TotalLadingCollect = totalLadingCollect;
    }

    public int getTotalWeightCollect() {
        return TotalWeightCollect;
    }

    public void setTotalWeightCollect(int totalWeightCollect) {
        TotalWeightCollect = totalWeightCollect;
    }

    public int getTotalAddressNotCollect() {
        return TotalAddressNotCollect;
    }

    public void setTotalAddressNotCollect(int totalAddressNotCollect) {
        TotalAddressNotCollect = totalAddressNotCollect;
    }

    public int getTotalLadingNotCollect() {
        return TotalLadingNotCollect;
    }

    public void setTotalLadingNotCollect(int totalLadingNotCollect) {
        TotalLadingNotCollect = totalLadingNotCollect;
    }

    public int getTotalWeightNotCollect() {
        return TotalWeightNotCollect;
    }

    public void setTotalWeightNotCollect(int totalWeightNotCollect) {
        TotalWeightNotCollect = totalWeightNotCollect;
    }
}
