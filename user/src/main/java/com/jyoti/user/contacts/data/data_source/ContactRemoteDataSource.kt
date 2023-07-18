package com.jyoti.user.contacts.data.data_source

import com.jyoti.user.contacts.data.model.AddContactRequestModel
import com.jyoti.user.contacts.data.model.AddContactResponseModel
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContactRemoteDataSource {

    @GET(ENDPOINT_CONTACTS)
    suspend fun listUsers(
        @Query("page") page: Int,
    ): Response<ContactsResponseModel>

    @POST(ENDPOINT_CONTACTS)
    suspend fun addUser(
        @Body requestModel: AddContactRequestModel
    ): Response<AddContactResponseModel>

    companion object {
        private const val ENDPOINT_CONTACTS = "/api/users"
    }
}
