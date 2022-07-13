package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class SearchOrderPostmanCollectRequest(
    @SerializedName("OrderPostmanID")
    val orderPostmanID:String?,
    @SerializedName("OrderID")
    val orderID:String?,
    @SerializedName("PostmanID")
    val postmanID:String?,
    @SerializedName("Status")
    val status:String?,
    @SerializedName("FromAssignDate")
    val fromAssignDate:String?,
    @SerializedName("ToAssignDate")
    val toAssignDate:String?
)