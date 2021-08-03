package com.example.login.ui.auth

sealed class AuthState {
    object Nothing: AuthState()
    object Loading : AuthState()
    object Success: AuthState()
    class Error(val message: String) : AuthState()
}
