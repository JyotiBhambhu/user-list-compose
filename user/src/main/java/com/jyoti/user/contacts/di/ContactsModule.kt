package com.jyoti.user.contacts.di

import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.network.ApiCaller
import com.jyoti.user.contacts.data.data_source.ContactRemoteDataSource
import com.jyoti.user.contacts.data.repo.ContactsRepoImpl
import com.jyoti.user.contacts.domain.repo.ContactsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object ContactsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideContactRemoteDataSource(retrofit: Retrofit): ContactRemoteDataSource =
        retrofit.create(ContactRemoteDataSource::class.java)

    @Provides
    @ActivityRetainedScoped
    fun provideContactsRepository(
        contactRemoteDataSource: ContactRemoteDataSource,
        apiCaller: ApiCaller,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): ContactsRepo {
        return ContactsRepoImpl(
            contactRemoteDataSource = contactRemoteDataSource,
            apiCaller = apiCaller,
            ioDispatcher = ioDispatcher
        )
    }

}
