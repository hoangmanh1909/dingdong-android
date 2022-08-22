package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class GetSolutionByReasonCodeRequest(
    @SerializedName("reasonCode")
    val reasonCode:String?
)