package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class GetVersionResponse(
    @SerializedName("Version")
    val version:String,
    @SerializedName("UrlDownload")
    val urlDownload:String,
    @SerializedName("Sort")
    val sort:String
)