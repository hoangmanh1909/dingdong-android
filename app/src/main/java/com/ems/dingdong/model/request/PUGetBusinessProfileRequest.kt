package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class PUGetBusinessProfileRequest(
    @SerializedName("CustomerID")
    val customerID:String?,
    @SerializedName("CustomerCode")
    val customerCode :String?
)