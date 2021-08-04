package com.dnieln7.login.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.dnieln7.login.domain.User
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesManager(private val dataStore: DataStore<Preferences>) {
    val user = dataStore.data.catch { catchError(it) }.map {
        val name = it[com.dnieln7.login.data.preferences.PreferencesKeys.NAME] ?: ""
        val lastName = it[com.dnieln7.login.data.preferences.PreferencesKeys.LAST_NAME] ?: ""
        val username = it[com.dnieln7.login.data.preferences.PreferencesKeys.USERNAME] ?: ""
        val email = it[com.dnieln7.login.data.preferences.PreferencesKeys.EMAIL] ?: ""

        if (name.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()) {
            User(
                name = name,
                lastName = lastName,
                username = username,
                email = email,
                password = ""
            )
        } else {
            null
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[com.dnieln7.login.data.preferences.PreferencesKeys.NAME] = user.name
            it[com.dnieln7.login.data.preferences.PreferencesKeys.LAST_NAME] = user.lastName
            it[com.dnieln7.login.data.preferences.PreferencesKeys.USERNAME] = user.username
            it[com.dnieln7.login.data.preferences.PreferencesKeys.EMAIL] = user.email
        }
    }

    suspend fun deleteUser() {
        dataStore.edit {
            it[com.dnieln7.login.data.preferences.PreferencesKeys.NAME] = ""
            it[com.dnieln7.login.data.preferences.PreferencesKeys.LAST_NAME] = ""
            it[com.dnieln7.login.data.preferences.PreferencesKeys.USERNAME] = ""
            it[com.dnieln7.login.data.preferences.PreferencesKeys.EMAIL] = ""
        }
    }

    private suspend fun FlowCollector<Preferences>.catchError(throwable: Throwable) {
        if (throwable is IOException) {
            emit(emptyPreferences())
        } else {
            throw throwable
        }
    }
}