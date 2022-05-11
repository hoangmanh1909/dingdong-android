package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class GetPostOfficeByCodeRequest (
    @SerializedName("Code")
    val Code:String?,
    @SerializedName("PostmanID")
    val PostmanID :String?

)