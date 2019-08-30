package com.ems.dingdong.model.request

data class SeaBankPaymentRequest(val MobileNumber: String, val StringInfo: String, val StringHeader: String,
                                 val StringValue: String, val SeaBankRetRefNumber: String, val ReceiverName: String?,
                                 val ReceiverAddress: String?, val ReceiverPhoneNumber: String?, val PIdType: String?,
                                 val PIDNumber: String?, val PIDDate: String?, val PIDWhere: String?, val Reason: String?
) {
    lateinit var OTPNumber: String
}
