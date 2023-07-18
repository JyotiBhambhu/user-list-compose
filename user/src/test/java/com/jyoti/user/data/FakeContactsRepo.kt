package com.jyoti.user.data

import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.data.model.AddContactResponseModel
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import com.jyoti.user.contacts.domain.repo.ContactsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

class FakeContactsRepo : ContactsRepo {
    private val contacts = ContactsResponseModel(
        page = 1,
        totalPages = 2,
        data = mutableListOf<ContactsResponseModel.DataResponseModel>().apply {
            add(
                ContactsResponseModel.DataResponseModel(
                    id = 1,
                    email = "michael.lawson@reqres.in",
                    fName = "Michael",
                    lName = "Lawson",
                    url = "https://reqres.in/img/faces/7-image.jpg"
                )
            )
        })

    override fun listUsers(page: Int): Flow<SealedResult<ContactsResponseModel?>> {
        return flow { emit(SealedResult.Response(contacts)) }
    }

    override fun addUser(name: String, job: String): Flow<SealedResult<AddContactResponseModel?>> {
        return flow {
            emit(
                SealedResult.Response(
                    AddContactResponseModel(
                        name = name,
                        job = job,
                        createdAt = Calendar.getInstance().time.toString(),
                        id = "1"
                    )
                )
            )
        }
    }
}