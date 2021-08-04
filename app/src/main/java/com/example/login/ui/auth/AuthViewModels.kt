package com.example.login.ui.auth

import androidx.lifecycle.*
import com.example.login.data.preferences.PreferencesManager
import com.example.login.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class LoginViewModel(private val preferencesManager: PreferencesManager) : ViewModel() {
    private val _uiState = MutableLiveData<AuthState>()

    val uiState: LiveData<AuthState> = _uiState

    fun login(username: String, password: String) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)

            if (username.isBlank() || password.isBlank() || username.length < 4) {
                withContext(Dispatchers.Main) {
                    _uiState.value = AuthState.Error("Wrong credentials")
                }
            } else {
                preferencesManager.saveUser(
                    User(
                        name = username.substring(0, (username.length / 2.0).roundToInt()),
                        lastName = username.substring(
                            (username.length / 2.0).roundToInt(),
                            username.length
                        ),
                        username = username,
                        email = "$username@gmail.com",
                        password = "",
                    )
                )
                withContext(Dispatchers.Main) { _uiState.value = AuthState.Success }
            }
        }
    }

    fun onLoginDone() {
        _uiState.value = AuthState.Nothing
    }

    class LoginViewModelFactory(private val preferencesManager: PreferencesManager) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(preferencesManager) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
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