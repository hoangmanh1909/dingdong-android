package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeCollectInfo {
    @SerializedName("TotalQuantity")
    @Expose
    private Integer totalQuantity;
    @SerializedName("TotalWeight")
    @Expose
    private Integer totalWeight;
    @SerializedName("TotalCODAmount")
    @Expose
    private Integer totalCODAmount;
    @SerializedName("TotalFee")
    @Expose
    private Integer totalFee;
    @SerializedName("TotalQuantityToday")
    @Expose
    private String totalQuantityToday;
    @SerializedName("TotalQuantityTodayNormal")
    @Expose
    private Integer totalQuantityTodayNormal;
    @SerializedName("TotalQuantityTodayCOD")
    @Expose
    private Integer totalQuantityTodayCOD;
    @SerializedName("TotalWeightToday")
    @Expose
    private Integer totalWeightToday;
    @SerializedName("TotalCODAmountToday")
    @Expose
    private Integer totalCODAmountToday;
    @SerializedName("TotalCODAmountTodayNormal")
    @Expose
    private Integer totalCODAmountTodayNormal;
    @SerializedName("TotalCODAmountTodayCOD")
    @Expose
    private Integer totalCODAmountTodayCOD;
    @SerializedName("TotalFeeToday")
    @Expose
    private Integer totalFeeToday;
    @SerializedName("TotalFeeTodayNormal")
    @Expose
    private Integer totalFeeTodayNormal;
    @SerializedName("TotalFeeTodayCOD")
    @Expose
    private Integer totalFeeTodayCOD;
    @SerializedName("TotalQuantityPast")
    @Expose
    private String totalQuantityPast;
    @SerializedName("TotalQuantityPastNormal")
    @Expose
    private Integer totalQuantityPastNormal;
    @SerializedName("TotalQuantityPastCOD")
    @Expose
    private Integer totalQuantityPastCOD;
    @SerializedName("TotalWeightPast")
    @Expose
    private Integer totalWeightPast;
    @SerializedName("TotalCODAmountPast")
    @Expose
    private Integer totalCODAmountPast;
    @SerializedName("TotalCODAmountPastNormal")
    @Expose
    private Integer totalCODAmountPastNormal;
    @SerializedName("TotalCODAmountPastCOD")
    @Expose
    private Integer totalCODAmountPastCOD;
    @SerializedName("TotalFeePast")
    @Expose
    private Integer totalFeePast;
    @SerializedName("TotalFeePastNormal")
    @Expose
    private Integer totalFeePastNormal;
    @SerializedName("TotalFeePastCOD")
    @Expose
    private Integer totalFeePastCOD;
    @SerializedName("TotalAddressNotCollect")
    @Expose
    private String totalAddressNotCollect;
    @SerializedName("TotalLadingNotCollect")
    @Expose
    private Integer totalLadingNotCollect;
    @SerializedName("TotalWeightNotCollect")
    @Expose
    private Integer totalWeightNotCollect;
    @SerializedName("TotalAddressCollect")
    @Expose
    private String totalAddressCollect;
    @SerializedName("TotalLadingCollect")
    @Expose
    private Integer totalLadingCollect;
    @SerializedName("TotalWeightCollect")
    @Expose
    private Integer totalWeightCollect;

    @SerializedName("TotalQuantityPastPA")
    @Expose
    private Integer totalQuantityPastPA;

    @SerializedName("TotalFeeTodayPA")
    @Expose
    private Integer totalFeeTodayPA;

    @SerializedName("TotalFeePastPA")
    @Expose
    private Integer totalFeePastPA;

    @SerializedName("TotalQuantityTodayPA")
    @Expose
    private Integer totalQuantityTodayPA;

    private String labelCollect;
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getLabelCollect() {
        return labelCollect;
    }

    public void setLabelCollect(String labelCollect) {
        this.labelCollect = labelCollect;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Integer getTotalCODAmount() {
        return totalCODAmount;
    }

    public void setTotalCODAmount(Integer totalCODAmount) {
        this.totalCODAmount = totalCODAmount;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getTotalQuantityToday() {
        return totalQuantityToday;
    }

    public void setTotalQuantityToday(String totalQuantityToday) {
        this.totalQuantityToday = totalQuantityToday;
    }

    public Integer getTotalQuantityTodayNormal() {
        return totalQuantityTodayNormal;
    }

    public void setTotalQuantityTodayNormal(Integer totalQuantityTodayNormal) {
        this.totalQuantityTodayNormal = totalQuantityTodayNormal;
    }

    public Integer getTotalQuantityTodayCOD() {
        return totalQuantityTodayCOD;
    }

    public void setTotalQuantityTodayCOD(Integer totalQuantityTodayCOD) {
        this.totalQuantityTodayCOD = totalQuantityTodayCOD;
    }

    public Integer getTotalWeightToday() {
        return totalWeightToday;
    }

    public void setTotalWeightToday(Integer totalWeightToday) {
        this.totalWeightToday = totalWeightToday;
    }

    public Integer getTotalCODAmountToday() {
        return totalCODAmountToday;
    }

    public void setTotalCODAmountToday(Integer totalCODAmountToday) {
        this.totalCODAmountToday = totalCODAmountToday;
    }

    public Integer getTotalCODAmountTodayNormal() {
        return totalCODAmountTodayNormal;
    }

    public void setTotalCODAmountTodayNormal(Integer totalCODAmountTodayNormal) {
        this.totalCODAmountTodayNormal = totalCODAmountTodayNormal;
    }

    public Integer getTotalCODAmountTodayCOD() {
        return totalCODAmountTodayCOD;
    }

    public void setTotalCODAmountTodayCOD(Integer totalCODAmountTodayCOD) {
        this.totalCODAmountTodayCOD = totalCODAmountTodayCOD;
    }

    public Integer getTotalFeeToday() {
        return totalFeeToday;
    }

    public void setTotalFeeToday(Integer totalFeeToday) {
        this.totalFeeToday = totalFeeToday;
    }

    public Integer getTotalFeeTodayNormal() {
        return totalFeeTodayNormal;
    }

    public void setTotalFeeTodayNormal(Integer totalFeeTodayNormal) {
        this.totalFeeTodayNormal = totalFeeTodayNormal;
    }

    public Integer getTotalFeeTodayCOD() {
        return totalFeeTodayCOD;
    }

    public void setTotalFeeTodayCOD(Integer totalFeeTodayCOD) {
        this.totalFeeTodayCOD = totalFeeTodayCOD;
    }

    public String getTotalQuantityPast() {
        return totalQuantityPast;
    }

    public void setTotalQuantityPast(String totalQuantityPast) {
        this.totalQuantityPast = totalQuantityPast;
    }

    public Integer getTotalQuantityPastNormal() {
        return totalQuantityPastNormal;
    }

    public void setTotalQuantityPastNormal(Integer totalQuantityPastNormal) {
        this.totalQuantityPastNormal = totalQuantityPastNormal;
    }

    public Integer getTotalQuantityPastCOD() {
        return totalQuantityPastCOD;
    }

    public void setTotalQuantityPastCOD(Integer totalQuantityPastCOD) {
        this.totalQuantityPastCOD = totalQuantityPastCOD;
    }

    public Integer getTotalWeightPast() {
        return totalWeightPast;
    }

    public void setTotalWeightPast(Integer totalWeightPast) {
        this.totalWeightPast = totalWeightPast;
    }

    public Integer getTotalCODAmountPast() {
        return totalCODAmountPast;
    }

    public void setTotalCODAmountPast(Integer totalCODAmountPast) {
        this.totalCODAmountPast = totalCODAmountPast;
    }

    public Integer getTotalCODAmountPastNormal() {
        return totalCODAmountPastNormal;
    }

    public void setTotalCODAmountPastNormal(Integer totalCODAmountPastNormal) {
        this.totalCODAmountPastNormal = totalCODAmountPastNormal;
    }

    public Integer getTotalCODAmountPastCOD() {
        return totalCODAmountPastCOD;
    }

    public void setTotalCODAmountPastCOD(Integer totalCODAmountPastCOD) {
        this.totalCODAmountPastCOD = totalCODAmountPastCOD;
    }

    public Integer getTotalFeePast() {
        return totalFeePast;
    }

    public void setTotalFeePast(Integer totalFeePast) {
        this.totalFeePast = totalFeePast;
    }

    public Integer getTotalFeePastNormal() {
        return totalFeePastNormal;
    }

    public void setTotalFeePastNormal(Integer totalFeePastNormal) {
        this.totalFeePastNormal = totalFeePastNormal;
    }

    public Integer getTotalFeePastCOD() {
        return totalFeePastCOD;
    }

    public void setTotalFeePastCOD(Integer totalFeePastCOD) {
        this.totalFeePastCOD = totalFeePastCOD;
    }

    public String getTotalAddressNotCollect() {
        return totalAddressNotCollect;
    }

    public void setTotalAddressNotCollect(String totalAddressNotCollect) {
        this.totalAddressNotCollect = totalAddressNotCollect;
    }

    public Integer getTotalLadingNotCollect() {
        return totalLadingNotCollect;
    }

    public void setTotalLadingNotCollect(Integer totalLadingNotCollect) {
        this.totalLadingNotCollect = totalLadingNotCollect;
    }

    public Integer getTotalWeightNotCollect() {
        return totalWeightNotCollect;
    }

    public void setTotalWeightNotCollect(Integer totalWeightNotCollect) {
        this.totalWeightNotCollect = totalWeightNotCollect;
    }

    public String getTotalAddressCollect() {
        return totalAddressCollect;
    }

    public void setTotalAddressCollect(String totalAddressCollect) {
        this.totalAddressCollect = totalAddressCollect;
    }

    public Integer getTotalLadingCollect() {
        return totalLadingCollect;
    }

    public void setTotalLadingCollect(Integer totalLadingCollect) {
        this.totalLadingCollect = totalLadingCollect;
    }

    public Integer getTotalWeightCollect() {
        return totalWeightCollect;
    }

    public void setTotalWeightCollect(Integer totalWeightCollect) {
        this.totalWeightCollect = totalWeightCollect;
    }

    public Integer getTotalQuantityPastPA() {
        return totalQuantityPastPA;
    }

    public void setTotalQuantityPastPA(Integer totalQuantityPastPA) {
        this.totalQuantityPastPA = totalQuantityPastPA;
    }

    public Integer getTotalFeeTodayPA() {
        return totalFeeTodayPA;
    }

    public void setTotalFeeTodayPA(Integer totalFeeTodayPA) {
        this.totalFeeTodayPA = totalFeeTodayPA;
    }

    public Integer getTotalFeePastPA() {
        return totalFeePastPA;
    }

    public void setTotalFeePastPA(Integer totalFeePastPA) {
        this.totalFeePastPA = totalFeePastPA;
    }

    public Integer getTotalQuantityTodayPA() {
        return totalQuantityTodayPA;
    }

    public void setTotalQuantityTodayPA(Integer totalQuantityTodayPA) {
        this.totalQuantityTodayPA = totalQuantityTodayPA;
    }
}
