package com.jyoti.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jyoti.core.data.data_source.TokenLocalDataSource
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("hrPreferencesDataStore")

    @Provides
    fun providePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideTokenLocalDataSource(
        datastore: DataStore<Preferences>,
    ): TokenLocalDataSource = TokenLocalDataSource(datastore)
}
