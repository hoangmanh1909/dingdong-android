package com.ems.dingdong.model.response

import com.ems.dingdong.model.SimpleResult
import com.google.gson.annotations.SerializedName

class SeaBankInquiryResponse : SimpleResult() {
    @SerializedName("Value")
    var data: SeaBankInquiryModel? = null
}