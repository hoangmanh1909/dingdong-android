package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

class CancelRouteModel(
    @SerializedName("Id")
    val Id: Int?,

    @SerializedName("PostmanId")
    val PostmanId: Int?
)