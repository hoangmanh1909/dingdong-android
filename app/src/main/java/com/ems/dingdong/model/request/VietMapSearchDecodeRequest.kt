package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class VietMapSearchDecodeRequest(
    @SerializedName("vpostcode")
    val vpostcode:String?

)