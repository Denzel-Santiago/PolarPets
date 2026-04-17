package com.polarsoft.polarpets.core.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_TOKEN = stringPreferencesKey("token")
        val KEY_USER_ID = intPreferencesKey("id_usuario")
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_ROL = intPreferencesKey("id_rol")
    }

    suspend fun saveSession(token: String, idUsuario: Int, username: String, idRol: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_ID] = idUsuario
            prefs[KEY_USERNAME] = username
            prefs[KEY_ROL] = idRol
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }

    val token: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val idUsuario: Flow<Int?> = context.dataStore.data.map { it[KEY_USER_ID] }
    val username: Flow<String?> = context.dataStore.data.map { it[KEY_USERNAME] }
    val idRol: Flow<Int?> = context.dataStore.data.map { it[KEY_ROL] }
}