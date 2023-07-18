package com.jyoti.user.contacts.data.repo

import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.extensions.emitAsFlow
import com.jyoti.core.network.ApiCaller
import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.data.data_source.ContactRemoteDataSource
import com.jyoti.user.contacts.data.model.AddContactRequestModel
import com.jyoti.user.contacts.data.model.AddContactResponseModel
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import com.jyoti.user.contacts.domain.repo.ContactsRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ContactsRepoImpl(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val contactRemoteDataSource: ContactRemoteDataSource,
    private val apiCaller: ApiCaller,
) : ContactsRepo {
    override fun listUsers(page: Int): Flow<SealedResult<ContactsResponseModel?>> =
        apiCaller.emitAsFlow {
            contactRemoteDataSource.listUsers(page)
        }.flowOn(ioDispatcher)

    override fun addUser(name: String, job: String): Flow<SealedResult<AddContactResponseModel?>> =
        apiCaller.emitAsFlow {
            contactRemoteDataSource.addUser(AddContactRequestModel(name = name, job = job))
        }.flowOn(ioDispatcher)
}