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
    private long totalCODAmount;
    @SerializedName("TotalFee")
    @Expose
    private long totalFee;
    @SerializedName("TotalQuantityToday")
    @Expose
    private String totalQuantityToday;
    @SerializedName("TotalQuantityTodayNormal")
    @Expose
    private Integer totalQuantityTodayNormal;

    @SerializedName("TotalQuantityTodayCOD")
    @Expose
    private long totalQuantityTodayCOD;
    @SerializedName("TotalWeightToday")
    @Expose
    private Integer totalWeightToday;
    @SerializedName("TotalCODAmountToday")
    @Expose
    private long totalCODAmountToday;
    @SerializedName("TotalCODAmountTodayNormal")
    @Expose
    private long totalCODAmountTodayNormal;
    @SerializedName("TotalCODAmountTodayCOD")
    @Expose
    private long totalCODAmountTodayCOD;
    @SerializedName("TotalFeeToday")
    @Expose
    private long totalFeeToday;
    @SerializedName("TotalFeeTodayNormal")
    @Expose
    private Integer totalFeeTodayNormal;
    @SerializedName("TotalFeeTodayCOD")
    @Expose
    private long totalFeeTodayCOD;
    @SerializedName("TotalQuantityPast")
    @Expose
    private String totalQuantityPast;
    @SerializedName("TotalQuantityPastNormal")
    @Expose
    private Integer totalQuantityPastNormal;
    @SerializedName("TotalQuantityPastCOD")
    @Expose
    private long totalQuantityPastCOD;
    @SerializedName("TotalWeightPast")
    @Expose
    private Integer totalWeightPast;
    @SerializedName("TotalCODAmountPast")
    @Expose
    private long totalCODAmountPast;
    @SerializedName("TotalCODAmountPastNormal")
    @Expose
    private long totalCODAmountPastNormal;
    @SerializedName("TotalCODAmountPastCOD")
    @Expose
    private long totalCODAmountPastCOD;
    @SerializedName("TotalFeePast")
    @Expose
    private Integer totalFeePast;
    @SerializedName("TotalFeePastNormal")
    @Expose
    private Integer totalFeePastNormal;
    @SerializedName("TotalFeePastCOD")
    @Expose
    private long totalFeePastCOD;
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

    public long getTotalCODAmount() {
        return totalCODAmount;
    }

    public void setTotalCODAmount(long totalCODAmount) {
        this.totalCODAmount = totalCODAmount;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
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

    public long getTotalQuantityTodayCOD() {
        return totalQuantityTodayCOD;
    }

    public void setTotalQuantityTodayCOD(long totalQuantityTodayCOD) {
        this.totalQuantityTodayCOD = totalQuantityTodayCOD;
    }

    public Integer getTotalWeightToday() {
        return totalWeightToday;
    }

    public void setTotalWeightToday(Integer totalWeightToday) {
        this.totalWeightToday = totalWeightToday;
    }

    public long getTotalCODAmountToday() {
        return totalCODAmountToday;
    }

    public void setTotalCODAmountToday(long totalCODAmountToday) {
        this.totalCODAmountToday = totalCODAmountToday;
    }

    public long getTotalCODAmountTodayNormal() {
        return totalCODAmountTodayNormal;
    }

    public void setTotalCODAmountTodayNormal(long totalCODAmountTodayNormal) {
        this.totalCODAmountTodayNormal = totalCODAmountTodayNormal;
    }

    public long getTotalCODAmountTodayCOD() {
        return totalCODAmountTodayCOD;
    }

    public void setTotalCODAmountTodayCOD(long totalCODAmountTodayCOD) {
        this.totalCODAmountTodayCOD = totalCODAmountTodayCOD;
    }

    public long getTotalFeeToday() {
        return totalFeeToday;
    }

    public void setTotalFeeToday(long totalFeeToday) {
        this.totalFeeToday = totalFeeToday;
    }

    public Integer getTotalFeeTodayNormal() {
        return totalFeeTodayNormal;
    }

    public void setTotalFeeTodayNormal(Integer totalFeeTodayNormal) {
        this.totalFeeTodayNormal = totalFeeTodayNormal;
    }

    public long getTotalFeeTodayCOD() {
        return totalFeeTodayCOD;
    }

    public void setTotalFeeTodayCOD(long totalFeeTodayCOD) {
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

    public long getTotalQuantityPastCOD() {
        return totalQuantityPastCOD;
    }

    public void setTotalQuantityPastCOD(long totalQuantityPastCOD) {
        this.totalQuantityPastCOD = totalQuantityPastCOD;
    }

    public Integer getTotalWeightPast() {
        return totalWeightPast;
    }

    public void setTotalWeightPast(Integer totalWeightPast) {
        this.totalWeightPast = totalWeightPast;
    }

    public long getTotalCODAmountPast() {
        return totalCODAmountPast;
    }

    public void setTotalCODAmountPast(long totalCODAmountPast) {
        this.totalCODAmountPast = totalCODAmountPast;
    }

    public long getTotalCODAmountPastNormal() {
        return totalCODAmountPastNormal;
    }

    public void setTotalCODAmountPastNormal(long totalCODAmountPastNormal) {
        this.totalCODAmountPastNormal = totalCODAmountPastNormal;
    }

    public long getTotalCODAmountPastCOD() {
        return totalCODAmountPastCOD;
    }

    public void setTotalCODAmountPastCOD(long totalCODAmountPastCOD) {
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

    public long getTotalFeePastCOD() {
        return totalFeePastCOD;
    }

    public void setTotalFeePastCOD(long totalFeePastCOD) {
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

    public String getLabelCollect() {
        return labelCollect;
    }

    public void setLabelCollect(String labelCollect) {
        this.labelCollect = labelCollect;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
