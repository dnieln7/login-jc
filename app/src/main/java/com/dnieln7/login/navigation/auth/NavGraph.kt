package com.dnieln7.login.navigation.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dnieln7.login.ui.auth.*

@Composable
fun NavGraph(
    modifier: Modifier,
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
) {
    val actions = remember(navHostController) { NavGraphActions(navHostController) }
    val loginState: AuthState by loginViewModel.uiState.observeAsState(AuthState.Nothing)
    val signUpState: AuthState by signUpViewModel.uiState.observeAsState(AuthState.Nothing)

    NavHost(
        navController = navHostController,
        startDestination = Destinations.Login.name,
        modifier = modifier
    ) {
        composable(Destinations.Login.name) {
            AuthBody(route = Destinations.Login) {

                LoginCard(
                    uiState = loginState,
                    onSubmit = { username, password ->
                        loginViewModel.login(
                            username,
                            password
                        )
                    },
                    onDone = { loginViewModel.onLoginDone() },
                    toSignUp = actions.navigateToSignUp
                )
            }
        }
        composable(Destinations.SignUp.name) {
            AuthBody(route = Destinations.SignUp) {
                SignUpCard(
                    uiState = signUpState,
                    onSubmit = { signUpViewModel.signup(it) },
                    onDone = { signUpViewModel.onSignUpDone() },
                    toLogin = actions.navigateToLogin,
                )
            }
        }
    }
}

class NavGraphActions(navHostController: NavHostController) {
    val navigateToSignUp: () -> Unit = {
        navHostController.navigate("${Destinations.SignUp}")
    }
    val navigateToLogin: () -> Unit = {
        navHostController.navigate("${Destinations.Login}")
    }
}