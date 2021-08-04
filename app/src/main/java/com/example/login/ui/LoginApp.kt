package com.example.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.login.data.preferences.PreferencesManager
import com.example.login.ui.auth.AuthScreen
import com.example.login.ui.home.HomeScreen

@Composable
fun LoginApp(viewModelContainer: ViewModelContainer, preferencesManager: PreferencesManager) {
    val userState by preferencesManager.user.collectAsState(initial = null)

    if (userState != null) {
        HomeScreen(preferencesManager = preferencesManager)
    } else {
        AuthScreen(viewModelContainer = viewModelContainer)
    }
}