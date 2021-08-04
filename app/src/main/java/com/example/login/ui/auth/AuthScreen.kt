package com.example.login.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.login.R
import com.example.login.navigation.Destinations

@Composable
fun AuthScreen(route: Destinations, content: @Composable () -> Unit) {
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
    AuthScreen(route = Destinations.SignUp) {
        LoginCard(
            uiState = AuthState.Success,
            onSubmit = { _, _ -> },
            toSignUp = {},
            onDone = {},
        )
    }
}