package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.login.navigation.Destinations
import com.example.login.navigation.NavGraph
import com.example.login.ui.auth.LoginViewModel
import com.example.login.ui.auth.SignUpViewModel
import com.example.login.ui.theme.LoginTheme

class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelContainer = ViewModelContainer(
            loginViewModel = loginViewModel,
            signUpViewModel = signUpViewModel,
        )

        setContent {
            LoginApp(viewModelContainer = viewModelContainer)
        }
    }
}

@Composable
fun LoginApp(viewModelContainer: ViewModelContainer) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = Destinations.getRouteName(backstackEntry.value?.destination?.route)

    LoginTheme {
        Scaffold {
            NavGraph(
                modifier = Modifier.padding(it),
                navHostController = navController,
                container = viewModelContainer,
            )
        }
    }
}

data class ViewModelContainer(
    val loginViewModel: LoginViewModel,
    val signUpViewModel: SignUpViewModel
)