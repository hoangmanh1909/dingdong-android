package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class GetMainViewResponse(
    @SerializedName("TotalQuantity")
    val totalQuantity:Long?,
    @SerializedName("TotalWeight")
    val totalWeight:Long?,
    @SerializedName("TotalCODAmount")
    val totalCODAmount:Long?,
    @SerializedName("TotalFee")
    val totalFee:Long?,
    @SerializedName("TotalQuantityToday")
    val totalQuantityToday:Long?,
    @SerializedName("TotalQuantityTodayNormal")
    val totalQuantityTodayNormal:Long?,
    @SerializedName("TotalQuantityTodayCOD")
    val totalQuantityTodayCOD:Long?,
    @SerializedName("TotalQuantityTodayPA")
    val totalQuantityTodayPA:Long?,
    @SerializedName("TotalWeightToday")
    val totalWeightToday:Long?,
    @SerializedName("TotalCODAmountToday")
    val totalCODAmountToday:Long?,
    @SerializedName("TotalCODAmountTodayNormal")
    val totalCODAmountTodayNormal:Long?,
    @SerializedName("TotalCODAmountTodayCOD")
    val totalCODAmountTodayCOD:Long?,
    @SerializedName("TotalFeeToday")
    val totalFeeToday:Long?,
    @SerializedName("TotalFeeTodayNormal")
    val totalFeeTodayNormal:Long?,
    @SerializedName("TotalFeeTodayCOD")
    val totalFeeTodayCOD:Long?,
    @SerializedName("TotalFeeTodayPA")
    val totalFeeTodayPA:Long?,
    @SerializedName("TotalQuantityPast")
    val totalQuantityPast:Long?,
    @SerializedName("TotalQuantityPastNormal")
    val totalQuantityPastNormal:Long?,
    @SerializedName("TotalQuantityPastCOD")
    val totalQuantityPastCOD:Long?,
    @SerializedName("TotalQuantityPastPA")
    val totalQuantityPastPA:Long?,
    @SerializedName("TotalWeightPast")
    val totalWeightPast:Long?,
    @SerializedName("TotalCODAmountPast")
    val totalCODAmountPast:Long?,
    @SerializedName("TotalCODAmountPastNormal")
    val totalCODAmountPastNormal:Long?,
    @SerializedName("TotalCODAmountPastCOD")
    val totalCODAmountPastCOD:Long?,
    @SerializedName("TotalFeePast")
    val totalFeePast:Long?,
    @SerializedName("TotalFeePastNormal")
    val totalFeePastNormal:Long?,
    @SerializedName("TotalFeePastCOD")
    val totalFeePastCOD:Long?,
    @SerializedName("TotalAddressNotCollect")
    val totalAddressNotCollect:Long?,
    @SerializedName("TotalLadingNotCollect")
    val totalLadingNotCollect:Long?,
    @SerializedName("TotalWeightNotCollect")
    val totalWeightNotCollect:Long?,
    @SerializedName("TotalAddressCollect")
    val totalAddressCollect:Long?,
    @SerializedName("TotalLadingCollect")
    val totalLadingCollect:Long?,
    @SerializedName("TotalWeightCollect")
    val totalWeightCollect:Long?

)