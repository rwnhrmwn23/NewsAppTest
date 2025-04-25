package com.onedev.newsapptest.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object UserPreferencesManager {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    private val KEY_NAME = stringPreferencesKey("user_name")
    private val KEY_EMAIL = stringPreferencesKey("user_email")
    private val KEY_PASSWORD = stringPreferencesKey("user_password")
    private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    suspend fun saveUser(context: Context, name: String, email: String, password: String) {
        context.dataStore.edit {
            it[KEY_NAME] = name
            it[KEY_EMAIL] = email
            it[KEY_PASSWORD] = password
            it[KEY_IS_LOGGED_IN] = true
        }
    }

    suspend fun loginUser(context: Context, email: String, password: String): Boolean {
        val prefs = context.dataStore.data.first()
        val savedEmail = prefs[KEY_EMAIL]
        val savedPassword = prefs[KEY_PASSWORD]
        return if (email == savedEmail && password == savedPassword) {
            context.dataStore.edit { it[KEY_IS_LOGGED_IN] = true }
            true
        } else false
    }

    suspend fun logout(context: Context) {
        context.dataStore.edit { it[KEY_IS_LOGGED_IN] = false }
    }

    fun isLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }

    fun getUserName(context: Context): Flow<String> =
        context.dataStore.data.map { it[KEY_NAME] ?: "User" }
}
