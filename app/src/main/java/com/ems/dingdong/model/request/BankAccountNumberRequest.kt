package com.ems.dingdong.model.request

import com.ems.dingdong.BuildConfig
import com.ems.dingdong.utiles.Utils.SHA256
import java.util.*

data class BankAccountNumberRequest(val MobileNumber: String, val IdentifyNumber: String, val BankAccountNumber: String) {
     var Signature: String = SHA256(MobileNumber + IdentifyNumber + BankAccountNumber + BuildConfig.PRIVATE_KEY).toUpperCase(Locale.getDefault())
}
