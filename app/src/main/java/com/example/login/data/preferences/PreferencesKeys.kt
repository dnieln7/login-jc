package com.example.login.data.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val NAME = stringPreferencesKey("NAME")
    val LAST_NAME = stringPreferencesKey("LAST_NAME")
    val USERNAME = stringPreferencesKey("USERNAME")
    val EMAIL = stringPreferencesKey("EMAIL")
}