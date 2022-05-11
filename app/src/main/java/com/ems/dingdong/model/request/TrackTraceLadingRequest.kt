package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrackTraceLadingRequest(
    @SerializedName("LadingCode")
    @Expose
    val ladingCode: String?,
    @SerializedName("POCode")
    @Expose
    val pOCode: String?,
    @SerializedName("Signature")
    @Expose
    val signature: String?
)