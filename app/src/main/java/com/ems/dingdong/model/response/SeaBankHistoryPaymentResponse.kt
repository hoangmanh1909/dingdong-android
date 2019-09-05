package com.ems.dingdong.model.response

import com.ems.dingdong.model.SimpleResult
import com.google.gson.annotations.SerializedName

class SeaBankHistoryPaymentResponse : SimpleResult() {
    @SerializedName("ListValue")
    var data: List<SeaBankHistoryPaymentModel>? = null
}