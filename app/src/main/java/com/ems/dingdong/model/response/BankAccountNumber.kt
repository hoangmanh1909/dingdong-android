package com.ems.dingdong.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BankAccountNumber {
    @SerializedName("AssigneeName")
    @Expose
    val assigneeName: String? = null
    @SerializedName("IdentifyNumber")
    @Expose
    val identifyNumber: String? = null
    @SerializedName("BankAccountNumber")
    @Expose
    val bankAccountNumber: String? = null
    @SerializedName("MandatorName")
    @Expose
    val mandatorName: String? = null


}
