package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.login.data.preferences.PreferencesManager
import com.example.login.navigation.Destinations
import com.example.login.navigation.NavGraph
import com.example.login.ui.auth.LoginViewModel
import com.example.login.ui.auth.SignUpViewModel
import com.example.login.ui.theme.LoginTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.login.data.preferences.PreferencesUtils.createDataStore
import com.example.login.ui.home.HomeScreen

class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel

    private val preferencesManager by lazy {
        PreferencesManager(createDataStore)
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
                LoginApp(viewModelContainer = viewModelContainer, preferencesManager = preferencesManager)
            }
        }
    }
}

@Composable
fun LoginApp(viewModelContainer: ViewModelContainer, preferencesManager: PreferencesManager) {
    val userState by preferencesManager.user.collectAsState(initial = null)

    if(userState != null) {
        HomeScreen(preferencesManager = preferencesManager)
    }else {
        AuthScreen(viewModelContainer = viewModelContainer)
    }
}

@Composable
fun AuthScreen(viewModelContainer: ViewModelContainer) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = Destinations.getRouteName(backstackEntry.value?.destination?.route)

    Scaffold {
        NavGraph(
            modifier = Modifier.padding(it),
            navHostController = navController,
            container = viewModelContainer,
        )
    }
}

data class ViewModelContainer(
    val loginViewModel: LoginViewModel,
    val signUpViewModel: SignUpViewModel
)