package com.example.login.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.login.R
import com.example.login.data.preferences.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(preferencesManager: PreferencesManager) {
    val scope = rememberCoroutineScope()

    val uiState by produceState(initialValue = DetailsState(isLoading = true)) {
        preferencesManager.user.collect {
            if (it != null) {
                value = DetailsState(user = it)
            }
        }
    }

    Scaffold(
        topBar = { HomeTopBar(signOut = { scope.launch(Dispatchers.IO) { preferencesManager.deleteUser() } }) },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                uiState.user != null -> {
                    UserDetails(user = uiState.user!!)
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(signOut: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Login App") },
        actions = {
            IconButton(onClick = signOut) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_exit),
                    contentDescription = "Sign out",
                )
            }
        },
    )
}

@Preview
@Composable
fun PreviewHomeBody() {

}