package com.jyoti.auth.data.model

import com.google.gson.annotations.SerializedName

data class AuthRequestModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
