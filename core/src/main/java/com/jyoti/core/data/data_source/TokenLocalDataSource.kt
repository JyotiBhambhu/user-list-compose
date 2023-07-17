package com.jyoti.core.data.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun getAccessToken(): String? = dataStore.data.map { preferences ->
        preferences[KEY_ACCESS_TOKEN]
    }.first()

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = token
        }
    }

    suspend fun nukeAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
        }
    }

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("keyAccessToken")
    }
}
