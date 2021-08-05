package com.dnieln7.login.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceLocator = (application as LoginApplication).serviceLocator

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModel.Factory(serviceLocator.preferencesManager, serviceLocator.userDataSource)
        ).get(LoginViewModel::class.java)

        signUpViewModel = ViewModelProvider(
            this,
            SignUpViewModel.Factory(serviceLocator.userDataSource)
        ).get(SignUpViewModel::class.java)

        val viewModelContainer = ViewModelContainer(
            loginViewModel = loginViewModel,
            signUpViewModel = signUpViewModel,
        )

        setContent {
            LoginTheme {
                LoadApp(
                    viewModelContainer = viewModelContainer,
                    preferencesManager = serviceLocator.preferencesManager,
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
fun LoadApp(viewModelContainer: ViewModelContainer, preferencesManager: PreferencesManager) {
    Surface(color = MaterialTheme.colors.primary) {
        var showLandingScreen by rememberSaveable { mutableStateOf(true) }

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