package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeliveryRouteRequest(
    @SerializedName("poCode")
    @Expose
    val poCode:String?
)