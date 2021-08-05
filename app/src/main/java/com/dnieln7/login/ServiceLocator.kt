package com.dnieln7.login

import android.content.Context
import com.dnieln7.login.data.preferences.PreferencesUtils.createDataStore
import com.dnieln7.login.data.source.user.UserInMemoryDataSource

class ServiceLocator(context: Context) {
    val preferencesManager by lazy {
        com.dnieln7.login.data.preferences.PreferencesManager(context.createDataStore)
    }

    val userDataSource = UserInMemoryDataSource()
}