package com.dnieln7.login.ui.auth

import androidx.lifecycle.*
import com.dnieln7.login.data.preferences.PreferencesManager
import com.dnieln7.login.data.source.user.UserDataSource
import com.dnieln7.login.domain.Result
import com.dnieln7.login.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class LoginViewModel(
    private val preferencesManager: PreferencesManager,
    private val userDataSource: UserDataSource
) : ViewModel() {
    private val _uiState = MutableLiveData<AuthState>()

    val uiState: LiveData<AuthState> = _uiState

    fun login(username: String, password: String) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            if (username.isBlank() || password.isBlank()) {
                withContext(Dispatchers.Main) {
                    _uiState.value = AuthState.Error("All fields are required")
                }
            } else {
                when (val result = userDataSource.login(username = username, password = password)) {
                    is Result.Error -> withContext(Dispatchers.Main) {
                        _uiState.value = AuthState.Error(message = result.reason)
                    }
                    is Result.Success<User> -> {
                        preferencesManager.saveUser(result.data)
                        withContext(Dispatchers.Main) { _uiState.value = AuthState.Success }
                    }
                }
            }
        }
    }

    fun onLoginDone() {
        _uiState.value = AuthState.Nothing
    }

    class Factory(
        private val preferencesManager: PreferencesManager,
        private val userDataSource: UserDataSource
    ) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(preferencesManager, userDataSource) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class SignUpViewModel(private val userDataSource: UserDataSource) : ViewModel() {
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
                when (val result = userDataSource.signUp(user = user)) {
                    is Result.Error -> withContext(Dispatchers.Main) {
                        _uiState.value = AuthState.Error(message = result.reason)
                    }
                    is Result.Success<String> -> withContext(Dispatchers.Main) {
                        _uiState.value = AuthState.Success
                    }
                }
            }
        }
    }

    fun onSignUpDone() {
        _uiState.value = AuthState.Nothing
    }

    class Factory(private val userDataSource: UserDataSource) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                return SignUpViewModel(userDataSource) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}