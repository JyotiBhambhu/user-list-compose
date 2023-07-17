package com.jyoti.core.base

import com.google.gson.annotations.SerializedName


open class BaseResponseModel<T> {
    @SerializedName("error")
    val error: String? = null
}
