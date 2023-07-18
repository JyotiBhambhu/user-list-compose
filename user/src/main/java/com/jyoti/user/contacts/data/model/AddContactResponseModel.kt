package com.jyoti.user.contacts.data.model

import com.google.gson.annotations.SerializedName
import com.jyoti.core.base.BaseResponseModel

data class AddContactResponseModel(
    @SerializedName("name") val name: String? = null,
    @SerializedName("job") val job: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null
) : BaseResponseModel<AddContactResponseModel>()
