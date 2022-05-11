package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BankLinkRequest(
    @SerializedName("UserName")
    @Expose
    val userName: String?,
    @SerializedName("UnitCode")
    @Expose
    val unitCode: String?
)