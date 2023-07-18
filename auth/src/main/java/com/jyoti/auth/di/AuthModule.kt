package com.jyoti.auth.di

import com.jyoti.auth.data.data_source.AuthRemoteDataSource
import com.jyoti.auth.data.repo.AuthRepoImpl
import com.jyoti.auth.domain.repo.AuthRepo
import com.jyoti.core.data.data_source.TokenLocalDataSource
import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.network.ApiCaller
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object AuthModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAuthRemoteDataSource(retrofit: Retrofit): AuthRemoteDataSource =
        retrofit.create(AuthRemoteDataSource::class.java)

    @Provides
    @ActivityRetainedScoped
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        tokenLocalDataSource: TokenLocalDataSource,
        apiCaller: ApiCaller,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): AuthRepo {
        return AuthRepoImpl(
            authRemoteDataSource = authRemoteDataSource,
            tokenLocalDataSource = tokenLocalDataSource,
            apiCaller = apiCaller,
            ioDispatcher = ioDispatcher
        )
    }

}
