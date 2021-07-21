package com.ems.dingdong.model.request

import com.ems.dingdong.BuildConfig
import com.ems.dingdong.utiles.Utils
import java.util.*

data class SeaBankInquiryRequest(val ProviderACNTCode: String, val MobileNumber: String, val IdentifyNumber: String, val BankAccountNumber: String,val BankAccountName :String,
                                 val  BankAccountMobileNumber : String,val Amount: Int, val AssigneeName: String, val MandatorName: String) {
    var Signature: String = Utils.SHA256(MobileNumber + IdentifyNumber + BankAccountNumber + Amount + BuildConfig.PRIVATE_KEY).toUpperCase(Locale.getDefault())
}
