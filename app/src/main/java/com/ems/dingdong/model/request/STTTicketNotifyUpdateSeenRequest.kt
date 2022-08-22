package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class STTTicketNotifyUpdateSeenRequest(
    @SerializedName("TicketCode")
    val ticketCode:String?
)