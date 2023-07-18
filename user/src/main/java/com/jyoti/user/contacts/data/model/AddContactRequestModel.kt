package com.jyoti.user.contacts.data.model

import com.google.gson.annotations.SerializedName

data class AddContactRequestModel(
    @SerializedName("name") val name: String,
    @SerializedName("job") val job: String
)
