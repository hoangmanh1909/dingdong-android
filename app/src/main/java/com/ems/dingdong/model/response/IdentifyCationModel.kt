package com.ems.dingdong.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IdentifyCationModel {
    @SerializedName("IdentifyId")
    @Expose
    val id: String? = null
    @SerializedName("IdentifyName")
    @Expose
    val name: String? = null

}
