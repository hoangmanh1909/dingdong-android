package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

class CheckLadingcode(
    @SerializedName("ParcelCode")
    val parcelCode: String?,

    @SerializedName("Signature")
    val signature: String?
)