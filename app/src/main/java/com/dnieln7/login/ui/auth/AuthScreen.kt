package com.dnieln7.login.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dnieln7.login.R
import com.dnieln7.login.navigation.auth.Destinations
import com.dnieln7.login.navigation.auth.NavGraph

@Composable
fun AuthScreen(
    loginViewModel: LoginViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = Destinations.getRouteName(backstackEntry.value?.destination?.route)

    Scaffold {
        NavGraph(
            modifier = Modifier.padding(it),
            navHostController = navController,
            loginViewModel = loginViewModel,
            signUpViewModel = signUpViewModel
        )
    }
}

@Composable
fun AuthBody(route: Destinations, content: @Composable () -> Unit) {
    Box {
        Image(
            painter = painterResource(
                id = when (route) {
                    Destinations.Login -> R.drawable.login
                    else -> R.drawable.signup
                }
            ),
            contentDescription = "Auth background",
            contentScale = ContentScale.Crop
        )
        content()
    }
}


@Preview
@Composable
fun AuthPreview() {
    AuthBody(route = Destinations.SignUp) {
        LoginCard(
            uiState = AuthState.Success,
            onSubmit = { _, _ -> },
            toSignUp = {},
            onDone = {},
        )
    }
}