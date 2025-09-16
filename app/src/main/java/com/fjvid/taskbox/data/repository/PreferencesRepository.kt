package com.fjvid.taskbox.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object PreferencesKeys{
        private val NOTIFICATION_PERMISSION = booleanPreferencesKey("notification_permission")
    }

    suspend fun getNotificationPermission(): Boolean{ // se accede a los datos del datastore
        return dataStore.data.map { prefs -> prefs[NOTIFICATION_PERMISSION] ?: false } // .map transforma esos datos para extraer solo el valor asociado a la clave "notification_permission" si no existe devuelve false
            .first()
    }

    suspend fun saveNotificationPermission(request: Boolean){
        dataStore.edit { prefs -> prefs[NOTIFICATION_PERMISSION] = request}
    }
}

