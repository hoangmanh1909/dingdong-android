package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class HistoryDeliveryRequest(
    @SerializedName("ParcelCode")
    val parcelCode: String
)