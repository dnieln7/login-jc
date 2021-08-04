package com.example.login.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.login.R
import com.example.login.data.preferences.PreferencesManager
import com.example.login.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun UserDetails(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Details", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(20.dp))
        InfoRow(label = "Name", text = user.name)
        InfoRow(label = "Last Name", text = user.lastName)
        InfoRow(label = "Username", text = user.username)
        InfoRow(label = "Email", text = user.email)
    }
}

data class DetailsState(
    val user: User? = null,
    val isLoading: Boolean = false,
)


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
        topBar = { LoginTopBar(signOut = { scope.launch(Dispatchers.IO) { preferencesManager.deleteUser() } }) },
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
fun LoginTopBar(signOut: () -> Unit) {
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

@Composable
fun InfoRow(label: String, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label :", style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Preview
@Composable
fun PreviewHomeBody() {

}