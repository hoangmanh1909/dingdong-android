package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class HistoryEWalletRequest(
    @SerializedName("POCode")
    val pOCode:String?,
    @SerializedName("PostmanCode")
    val postmanCode :String?,
    @SerializedName("PostmanTel")
    val postmanTel :String?,
    @SerializedName("BankCode")
    val bankCode:String?
)