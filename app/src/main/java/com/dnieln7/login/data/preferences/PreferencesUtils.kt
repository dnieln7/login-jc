package com.dnieln7.login.data.preferences

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore

object PreferencesUtils {
    private const val PREFERENCES_NAME = "login_preferences"

    val Context.createDataStore by preferencesDataStore(
        name = PREFERENCES_NAME,
    )
}