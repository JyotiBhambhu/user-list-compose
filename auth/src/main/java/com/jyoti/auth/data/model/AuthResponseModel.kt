package com.jyoti.auth.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.jyoti.core.base.BaseResponseModel

@Keep
data class AuthResponseModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("token") val data: String?,
) : BaseResponseModel<AuthResponseModel>()
