package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

class GetPaypostError(
    @SerializedName("fromDate")
    val fromDate: String?,

    @SerializedName("toDate")
    val toDate: String?
)