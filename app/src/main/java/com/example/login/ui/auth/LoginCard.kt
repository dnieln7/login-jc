package com.example.login.ui.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.login.utils.KeyOptions
import com.example.login.utils.KeyOptions.doneAction
import com.example.login.utils.KeyOptions.downAction

@Composable
fun LoginCard(
    uiState: AuthState,
    onSubmit: (username: String, password: String) -> Unit,
    onDone: () -> Unit,
    toSignUp: () -> Unit,
) {
    var username by rememberSaveable(key = "usernameState") { mutableStateOf("") }
    var password by rememberSaveable(key = "passwordState") { mutableStateOf("") }
    var obscurePassword by rememberSaveable(key = "obscureState") { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current
    val noFilledColor = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
    ) {
        Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Username") },
                    value = username,
                    onValueChange = { username = it },
                    colors = noFilledColor,
                    keyboardOptions = KeyOptions.textNext,
                    keyboardActions = focusManager.downAction(),
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Password") },
                    value = password,
                    onValueChange = { password = it },
                    colors = noFilledColor,
                    keyboardOptions = KeyOptions.passwordDone,
                    keyboardActions = focusManager.doneAction(),
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable { obscurePassword = !obscurePassword },
                            imageVector = if (obscurePassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = "Password Toggle"
                        )
                    },
                    visualTransformation = if (obscurePassword) PasswordVisualTransformation() else VisualTransformation.None,
                )

                Spacer(modifier = Modifier.height(30.dp))
                if (uiState != AuthState.Loading)
                    Column {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSubmit(username, password) }) {
                            Text(text = "LOGIN")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        TextButton(modifier = Modifier.fillMaxWidth(), onClick = { toSignUp() }) {
                            Text(text = "SIGN UP")
                        }
                    }
                if (uiState == AuthState.Loading)
                    CircularProgressIndicator()
                if (uiState is AuthState.Error)
                    Column {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Error: ${uiState.message}",
                            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error)
                        )
                    }
                if (uiState == AuthState.Success) {
                    Toast.makeText(LocalContext.current, "Logged in", Toast.LENGTH_SHORT).show()
                    onDone()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginCard(
        uiState = AuthState.Nothing,
        onSubmit = { _, _ -> },
        onDone = {},
        toSignUp = {},
    )
}