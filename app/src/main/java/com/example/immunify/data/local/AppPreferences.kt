package com.example.immunify.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("app_preferences")

class AppPreferences(private val context: Context) {

    companion object {
        private val FIRST_TIME_KEY = booleanPreferencesKey("is_first_time")
    }

    val isFirstTime: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[FIRST_TIME_KEY] ?: true   // default: true (pertama kali buka)
    }

    suspend fun setNotFirstTime() {
        context.dataStore.edit { prefs ->
            prefs[FIRST_TIME_KEY] = false
        }
    }
}
