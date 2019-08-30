package com.ems.dingdong.model.response

class BaseResponseModel<T> {
    var Code: String? = null
    var ListValue: T? = null
    var Value: T? = null
    var Message: String? = null
}