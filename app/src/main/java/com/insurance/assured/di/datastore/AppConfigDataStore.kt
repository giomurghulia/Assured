package com.insurance.assured.di.datastore

import androidx.datastore.DataStore

import androidx.datastore.preferences.Preferences

import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.remove
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppConfigDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
     suspend fun setPassCode(value: String?) {
        if (value.isNullOrEmpty()) {
            val dataStoreKey = preferencesKey<String>(passCodeKey)
            dataStore.edit { passCode ->
                passCode.remove(dataStoreKey)
            }
        } else {
            val dataStoreKey = preferencesKey<String>(passCodeKey)
            dataStore.edit { passCode ->
                passCode[dataStoreKey] = value
            }
        }
    }
     suspend fun getPassCode(): String? {
        val dataStoreKey = preferencesKey<String>(passCodeKey)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
    
    companion object{
        const val passCodeKey = "pass_code"
    }
}