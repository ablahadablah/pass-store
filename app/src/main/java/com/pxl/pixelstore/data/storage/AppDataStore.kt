package com.pxl.pixelstore.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")
        
//        private val KEY_MASTER_PASSWORD = stringPreferencesKey("KEY_MASTER_PASSWORD")
        private val KEY_SALT = stringPreferencesKey("KEY_SALT")
        private val KEY_VALIDATION_STRING = stringPreferencesKey("KEY_VALIDATION_STRING")
    }
    
    suspend fun putString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }
    
    suspend fun putLong(key: String, value: Long) {
        val prefKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }
    
    fun getString(key: String, defaultValue: String): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[prefKey] ?: defaultValue
        }
    }
    
    fun getLongOrNull(key: String): Flow<Long?> {
        val prefKey = longPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[prefKey]
        }
    }
    
    fun getLong(key: String, defaultValue: Long): Flow<Long> {
        val prefKey = longPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[prefKey] ?: defaultValue
        }
    }
    
    suspend fun remove(key: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(longPreferencesKey(key))
        }
    }
    
    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    

    suspend fun putSalt(value: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_SALT] = value
        }
    }

    fun getSalt(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_SALT] ?: ""
        }
    }

    suspend fun putValidationString(value: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_VALIDATION_STRING] = value
        }
    }

    fun getValidationString(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_VALIDATION_STRING] ?: ""
        }
    }
}