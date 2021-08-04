package com.dnieln7.login.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import com.dnieln7.login.LoginApplication
import com.dnieln7.login.ui.auth.LoginViewModel
import com.dnieln7.login.ui.auth.SignUpViewModel
import com.dnieln7.login.ui.theme.LoginTheme

class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginApplication = application as LoginApplication

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModel.LoginViewModelFactory(loginApplication.serviceLocator.preferencesManager)
        ).get(LoginViewModel::class.java)

        val viewModelContainer = ViewModelContainer(
            loginViewModel = loginViewModel,
            signUpViewModel = signUpViewModel,
        )

        setContent {
            LoginTheme {
                LoadApp(
                    viewModelContainer = viewModelContainer,
                    preferencesManager = loginApplication.serviceLocator.preferencesManager,
                )
            }
        }
    }
}

data class ViewModelContainer(
    val loginViewModel: LoginViewModel,
    val signUpViewModel: SignUpViewModel
)

@Composable
fun LoadApp(viewModelContainer: ViewModelContainer, preferencesManager: com.dnieln7.login.data.preferences.PreferencesManager) {
    Surface(color = MaterialTheme.colors.primary) {
        var showLandingScreen by remember { mutableStateOf(true) }

        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            LoginApp(
                viewModelContainer = viewModelContainer,
                preferencesManager = preferencesManager,
            )
        }
    }
}