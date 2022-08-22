package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class LocationAddNewRequest(
    @SerializedName("PostmanID")
    val postmanID:String,
    @SerializedName("Latitude")
    val latitude:String,
    @SerializedName("Longitude")
    val longitude:String,
    @SerializedName("Signature")
    val signature:String
)