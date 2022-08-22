package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("Balance")
    val balance: Int,
    @SerializedName("AmountMax")
    val amountMax: Int
)