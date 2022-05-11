package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActiveRequest(
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String?,
    @SerializedName("ActiveCode")
    @Expose
    val activeCode: String?,
    @SerializedName("CodeDeviceActive")
    @Expose
    val codeDeviceActive: String?,
    @SerializedName("Signature")
    @Expose
    val signature: String?
)