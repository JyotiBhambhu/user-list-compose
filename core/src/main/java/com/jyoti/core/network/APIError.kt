package com.jyoti.core.network

import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("error")
    val error: String? = null,
)
