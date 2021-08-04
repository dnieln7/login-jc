package com.dnieln7.login.ui.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dnieln7.login.domain.User
import com.dnieln7.login.utils.KeyOptions
import com.dnieln7.login.utils.KeyOptions.doneAction
import com.dnieln7.login.utils.KeyOptions.downAction
import com.dnieln7.login.utils.TextValidation

@Composable
fun SignUpCard(
    uiState: AuthState,
    onSubmit: (user: User) -> Unit,
    onDone: () -> Unit,
    toLogin: () -> Unit,
) {

    val (name, setName) = rememberSaveable(key = "name") { mutableStateOf("") }
    val (lastName, setLastName) = rememberSaveable(key = "lastName") { mutableStateOf("") }
    val (username, setUsername) = rememberSaveable(key = "username") { mutableStateOf("") }
    val (email, setEmail) = rememberSaveable(key = "email") { mutableStateOf("") }

    val (password, setPassword) = rememberSaveable(key = "password") { mutableStateOf("") }
    val (password2, setPassword2) = rememberSaveable(key = "password2") { mutableStateOf("") }
    val (hidePassword, setHidePassword) = rememberSaveable(key = "obscurePassword") {
        mutableStateOf(true)
    }
    val (hidePassword2, setHidePassword2) = rememberSaveable(key = "obscurePassword2") {
        mutableStateOf(true)
    }

    val validEmail = TextValidation.isEmail(email) || email.isEmpty()

    val focusManager = LocalFocusManager.current
    val noFilledColor = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

    fun validateForm() {
        if (password == password2) {
            onSubmit(
                User(
                    name = name,
                    lastName = lastName,
                    username = username,
                    email = email,
                    password = password,
                )
            )
        }
    }

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
                    label = { Text(text = "Name") },
                    value = name,
                    onValueChange = { setName(it) },
                    colors = noFilledColor,
                    keyboardOptions = KeyOptions.textNext,
                    keyboardActions = focusManager.downAction()
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Last name") },
                    value = lastName,
                    onValueChange = { setLastName(it) },
                    colors = noFilledColor,
                    keyboardOptions = KeyOptions.textNext,
                    keyboardActions = focusManager.downAction()
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Username") },
                    value = username,
                    onValueChange = { setUsername(it) },
                    colors = noFilledColor,
                    keyboardOptions = KeyOptions.textNext,
                    keyboardActions = focusManager.downAction()
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { if (validEmail) Text(text = "Email") else Text(text = "This is not a valid email") },
                    value = email,
                    onValueChange = { setEmail(it) },
                    colors = noFilledColor,
                    isError = !validEmail,
                    keyboardOptions = KeyOptions.emailNext,
                    keyboardActions = focusManager.downAction()
                )
                DoublePasswordInput(
                    password = password,
                    setPassword = setPassword,
                    password2 = password2,
                    setPassword2 = setPassword2,
                    hidePassword = hidePassword,
                    setHidePassword = setHidePassword,
                    hidePassword2 = hidePassword2,
                    setHidePassword2 = setHidePassword2
                )
                Spacer(modifier = Modifier.height(30.dp))
                if (uiState != AuthState.Loading)
                    Column {
                        Button(modifier = Modifier.fillMaxWidth(), onClick = { validateForm() }) {
                            Text(text = "SIGN UP")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        TextButton(modifier = Modifier.fillMaxWidth(), onClick = { toLogin() }) {
                            Text(text = "I HAVE AN ACCOUNT")
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
                    Toast.makeText(
                        LocalContext.current,
                        "Thanks for signing up",
                        Toast.LENGTH_SHORT
                    ).show()
                    toLogin()
                    onDone()
                }
            }
        }
    }
}

@Composable
fun DoublePasswordInput(
    password: String,
    setPassword: (String) -> Unit,
    password2: String,
    setPassword2: (String) -> Unit,
    hidePassword: Boolean,
    setHidePassword: (Boolean) -> Unit,
    hidePassword2: Boolean,
    setHidePassword2: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val passwordValid = TextValidation.isPasswordValid(password) || password.isEmpty()
    val passwordMatch = password == password2

    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = { if (passwordValid) Text(text = "Password") else Text(text = "Your password is not secure") },
        value = password,
        onValueChange = { setPassword(it) },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        isError = !passwordValid,
        keyboardOptions = KeyOptions.passwordNext,
        keyboardActions = focusManager.downAction(),
        visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { setHidePassword(!hidePassword) },
                imageVector = if (hidePassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "Password Toggle"
            )
        }
    )
    if (!passwordValid) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "* Secure passwords must contain an upper case letter, a number, an special symbol and must be at least 8 characters long.",
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error)
        )
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = { if (passwordMatch) Text(text = "Confirm password") else Text(text = "Passwords must be equal") },
        value = password2,
        onValueChange = { setPassword2(it) },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        isError = !passwordMatch,
        keyboardOptions = KeyOptions.passwordDone,
        keyboardActions = focusManager.doneAction(),
        visualTransformation = if (hidePassword2) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { setHidePassword2(!hidePassword2) },
                imageVector = if (hidePassword2) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "Confirm password Toggle"
            )
        }
    )

}

@Preview
@Composable
fun PreviewSignUp() {
    SignUpCard(
        uiState = AuthState.Nothing,
        onSubmit = { },
        onDone = {},
        toLogin = {},
    )
}