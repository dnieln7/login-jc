package com.dnieln7.login.domain

sealed class Result<out R> {
    class Success<T>(val data: T) : Result<T>()
    class Error(val reason: String) : Result<Nothing>()
}
