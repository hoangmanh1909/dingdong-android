package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class GetMainViewResponse(
    @SerializedName("TotalQuantity")
    val totalQuantity:Int?,
    @SerializedName("TotalWeight")
    val totalWeight:Int?,
    @SerializedName("TotalCODAmount")
    val totalCODAmount:Int?,
    @SerializedName("TotalFee")
    val totalFee:Int?,
    @SerializedName("TotalQuantityToday")
    val totalQuantityToday:Int?,
    @SerializedName("TotalQuantityTodayNormal")
    val totalQuantityTodayNormal:Int?,
    @SerializedName("TotalQuantityTodayCOD")
    val totalQuantityTodayCOD:Int?,
    @SerializedName("TotalQuantityTodayPA")
    val totalQuantityTodayPA:Int?,
    @SerializedName("TotalWeightToday")
    val totalWeightToday:Int?,
    @SerializedName("TotalCODAmountToday")
    val totalCODAmountToday:Int?,
    @SerializedName("TotalCODAmountTodayNormal")
    val totalCODAmountTodayNormal:Int?,
    @SerializedName("TotalCODAmountTodayCOD")
    val totalCODAmountTodayCOD:Int?,
    @SerializedName("TotalFeeToday")
    val totalFeeToday:Int?,
    @SerializedName("TotalFeeTodayNormal")
    val totalFeeTodayNormal:Int?,
    @SerializedName("TotalFeeTodayCOD")
    val totalFeeTodayCOD:Int?,
    @SerializedName("TotalFeeTodayPA")
    val totalFeeTodayPA:Int?,
    @SerializedName("TotalQuantityPast")
    val totalQuantityPast:Int?,
    @SerializedName("TotalQuantityPastNormal")
    val totalQuantityPastNormal:Int?,
    @SerializedName("TotalQuantityPastCOD")
    val totalQuantityPastCOD:Int?,
    @SerializedName("TotalQuantityPastPA")
    val totalQuantityPastPA:Int?,
    @SerializedName("TotalWeightPast")
    val totalWeightPast:Int?,
    @SerializedName("TotalCODAmountPast")
    val totalCODAmountPast:Int?,
    @SerializedName("TotalCODAmountPastNormal")
    val totalCODAmountPastNormal:Int?,
    @SerializedName("TotalCODAmountPastCOD")
    val totalCODAmountPastCOD:Int?,
    @SerializedName("TotalFeePast")
    val totalFeePast:Int?,
    @SerializedName("TotalFeePastNormal")
    val totalFeePastNormal:Int?,
    @SerializedName("TotalFeePastCOD")
    val totalFeePastCOD:Int?,
    @SerializedName("TotalAddressNotCollect")
    val totalAddressNotCollect:Int?,
    @SerializedName("TotalLadingNotCollect")
    val totalLadingNotCollect:Int?,
    @SerializedName("TotalWeightNotCollect")
    val totalWeightNotCollect:Int?,
    @SerializedName("TotalAddressCollect")
    val totalAddressCollect:Int?,
    @SerializedName("TotalLadingCollect")
    val totalLadingCollect:Int?,
    @SerializedName("TotalWeightCollect")
    val totalWeightCollect:Int?

)