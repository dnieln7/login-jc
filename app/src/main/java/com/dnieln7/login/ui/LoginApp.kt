package com.dnieln7.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dnieln7.login.data.preferences.PreferencesManager
import com.dnieln7.login.ui.auth.AuthScreen
import com.dnieln7.login.ui.home.HomeScreen

@Composable
fun LoginApp(preferencesManager: PreferencesManager) {
    val userState by preferencesManager.user.collectAsState(initial = null)

    if (userState != null) {
        HomeScreen(preferencesManager = preferencesManager)
    } else {
        AuthScreen()
    }
}