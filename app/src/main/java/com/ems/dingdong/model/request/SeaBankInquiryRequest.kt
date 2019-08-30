package com.ems.dingdong.model.request

data class SeaBankInquiryRequest(val MobileNumber: String, val IdentifyNumber: String, val BankAccountNumber: String,
                                 val Amount: Int, val AssigneeName: String, val MandatorName: String
)
