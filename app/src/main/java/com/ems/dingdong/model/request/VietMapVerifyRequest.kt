package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class VietMapVerifyRequest(
    @SerializedName("Id")
    val id:String?,
    @SerializedName("IdUser")
    val idUser:String?,
    @SerializedName("IsVerify")
    val isVerify:Boolean?,
    @SerializedName("Layer")
    val layer:String?
)