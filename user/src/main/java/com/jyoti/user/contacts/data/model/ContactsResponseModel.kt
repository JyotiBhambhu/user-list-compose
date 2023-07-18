package com.jyoti.user.contacts.data.model

import com.google.gson.annotations.SerializedName
import com.jyoti.core.base.BaseResponseModel

data class ContactsResponseModel(
    @SerializedName("page") val page: Int,
    @SerializedName("per_page") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: String,
    @SerializedName("data") val data: DataResponseModel,
) : BaseResponseModel<ContactsResponseModel>() {
    data class DataResponseModel(
        @SerializedName("id") val id: Int,
        @SerializedName("email") val email: String,
        @SerializedName("first_name") val fName: String,
        @SerializedName("last_name") val lName: String,
        @SerializedName("avatar") val url: String,
    )
}
