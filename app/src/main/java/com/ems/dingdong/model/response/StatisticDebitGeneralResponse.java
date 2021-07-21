package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDebitGeneralResponse {

    @SerializedName("DebitSuccessQuantity")
    private String successQuantity;
    @SerializedName("DebitSuccessAmount")
    private String successAmount;
    @SerializedName("DebitErrorQuantity")
    private String errorQuantity;
    @SerializedName("DebitErrorAmount")
    private String errorAmount;


    /// <summary>
    /// Tổng khoản thu
    /// </summary>
    @SerializedName("TotalDebit")
    private int TotalDebit;

    /// <summary>
    /// Tổng bưu gửi
    /// </summary>
    @SerializedName("TotalLading")
    private int TotalLading;

    public int getTotalDebit() {
        return TotalDebit;
    }

    public void setTotalDebit(int totalDebit) {
        TotalDebit = totalDebit;
    }

    public int getTotalLading() {
        return TotalLading;
    }

    public void setTotalLading(int totalLading) {
        TotalLading = totalLading;
    }

    public String getSuccessQuantity() {
        return successQuantity;
    }

    public String getSuccessAmount() {
        return successAmount;
    }

    public String getErrorQuantity() {
        return errorQuantity;
    }

    public String getErrorAmount() {
        return errorAmount;
    }
}
