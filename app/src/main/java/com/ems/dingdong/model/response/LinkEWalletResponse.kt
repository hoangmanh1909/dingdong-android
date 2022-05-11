package com.ems.dingdong.model.response

import com.ems.dingdong.model.SimpleResult
import com.google.gson.annotations.SerializedName

data class LinkEWalletResponse(
    @SerializedName("Value")
    val repose:String?
)