package com.jyoti.user.contacts.data.model

import com.google.gson.annotations.SerializedName
import com.jyoti.core.base.BaseResponseModel

data class ContactsResponseModel(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("per_page") val limit: Int? = null,
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("data") val data: List<DataResponseModel>? = null,
) : BaseResponseModel<ContactsResponseModel>() {
    data class DataResponseModel(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("email") val email: String? = null,
        @SerializedName("first_name") val fName: String? = null,
        @SerializedName("last_name") val lName: String? = null,
        @SerializedName("avatar") val url: String? = null,
    )
}
