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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    val user = dataStore.data.catch { catchError(it) }.map {
        val name = it[PreferencesKeys.NAME] ?: ""
        val lastName = it[PreferencesKeys.LAST_NAME] ?: ""
        val username = it[PreferencesKeys.USERNAME] ?: ""
        val email = it[PreferencesKeys.EMAIL] ?: ""

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
            it[PreferencesKeys.NAME] = user.name
            it[PreferencesKeys.LAST_NAME] = user.lastName
            it[PreferencesKeys.USERNAME] = user.username
            it[PreferencesKeys.EMAIL] = user.email
        }
    }

    suspend fun deleteUser() {
        dataStore.edit {
            it[PreferencesKeys.NAME] = ""
            it[PreferencesKeys.LAST_NAME] = ""
            it[PreferencesKeys.USERNAME] = ""
            it[PreferencesKeys.EMAIL] = ""
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