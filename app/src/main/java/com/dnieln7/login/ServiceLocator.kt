package com.dnieln7.login

import android.content.Context
import com.dnieln7.login.data.preferences.PreferencesUtils.createDataStore

class ServiceLocator(context: Context) {
    val preferencesManager by lazy {
        com.dnieln7.login.data.preferences.PreferencesManager(context.createDataStore)
    }
}