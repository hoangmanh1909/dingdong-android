package com.ems.dingdong.model.request

import com.ems.dingdong.BuildConfig
import com.ems.dingdong.utiles.Utils
import java.util.*

data class SeaBankPaymentRequest(val MobileNumber: String, val StringInfo: String, val StringHeader: String,
                                 val StringValue: String, val SeaBankRetRefNumber: String, val ReceiverName: String?,
                                 val ReceiverAddress: String?, val ReceiverPhoneNumber: String?, val PIdType: String?,
                                 val PIDNumber: String?, val PIDDate: String?, val PIDWhere: String?, val Reason: String?
                                 ,val ProviderACNTCode: String?, val TraceId: String?, val BankAccountMobileNumber: String?
) {
    lateinit var OTPNumber: String
    var Signature: String? = null
}
