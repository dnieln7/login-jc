package com.dnieln7.login.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dnieln7.login.data.preferences.PreferencesManager
import com.dnieln7.login.navigation.Destinations
import com.dnieln7.login.navigation.NavGraph
import com.dnieln7.login.ui.auth.LoginViewModel
import com.dnieln7.login.ui.auth.SignUpViewModel
import com.dnieln7.login.ui.theme.LoginTheme
import com.dnieln7.login.data.preferences.PreferencesUtils.createDataStore

class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel

    private val preferencesManager by lazy {
        com.dnieln7.login.data.preferences.PreferencesManager(createDataStore)
    }

    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModel.LoginViewModelFactory(preferencesManager)
        ).get(LoginViewModel::class.java)

        val viewModelContainer = ViewModelContainer(
            loginViewModel = loginViewModel,
            signUpViewModel = signUpViewModel,
        )

        setContent {
            LoginTheme {
                LoadApp(
                    viewModelContainer = viewModelContainer,
                    preferencesManager = preferencesManager,
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