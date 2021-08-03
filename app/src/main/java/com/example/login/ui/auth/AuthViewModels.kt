package com.example.login.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val _uiState = MutableLiveData<AuthState>()

    val uiState: LiveData<AuthState> = _uiState

    fun login(username: String, password: String) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)

            if (username.isBlank() || password.isBlank()) {
                withContext(Dispatchers.Main) {
                    _uiState.value = AuthState.Error("Wrong credentials")
                }
            } else {
                withContext(Dispatchers.Main) { _uiState.value = AuthState.Success }
            }
        }
    }

    fun onLoginDone() {
        _uiState.value = AuthState.Nothing
    }
}

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableLiveData<AuthState>()

    val uiState: LiveData<AuthState> = _uiState

    fun signup(user: User) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)

            if (user.name.isEmpty() || user.lastName.isEmpty() || user.username.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
                withContext(Dispatchers.Main) {
                    _uiState.value = AuthState.Error("All fields are required")
                }
            } else {
                withContext(Dispatchers.Main) { _uiState.value = AuthState.Success }
            }
        }
    }

    fun onSignUpDone() {
        _uiState.value = AuthState.Nothing
    }
}