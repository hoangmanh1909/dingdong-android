package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class VmRouteV2Request(
    @SerializedName("points")
    val points:List<String>,
    @SerializedName("vpostcode")
    val vpostcode:String?
)