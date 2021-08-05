package com.dnieln7.login.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModelProvider
import com.dnieln7.login.LoginApplication
import com.dnieln7.login.data.preferences.PreferencesManager
import com.dnieln7.login.ui.auth.LoginViewModel
import com.dnieln7.login.ui.auth.SignUpViewModel
import com.dnieln7.login.ui.theme.LoginTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginTheme {
                LoadApp(preferencesManager = preferencesManager)
            }
        }
    }
}

@Composable
fun LoadApp(preferencesManager: PreferencesManager) {
    Surface(color = MaterialTheme.colors.primary) {
        var showLandingScreen by rememberSaveable { mutableStateOf(true) }

        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            LoginApp(preferencesManager = preferencesManager)
        }
    }
}