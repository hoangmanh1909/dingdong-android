package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class SearchCallCenterRequest(
    @SerializedName("PostmanID")
    val postmanID:String,
    @SerializedName("FromDate")
    val fromDate:String,
    @SerializedName("ToDate")
    val toDate:String,
    @SerializedName("Signature")
    val signature:String
)