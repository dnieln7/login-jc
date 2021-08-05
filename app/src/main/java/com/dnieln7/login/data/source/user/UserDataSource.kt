package com.dnieln7.login.data.source.user

import com.dnieln7.login.domain.Result
import com.dnieln7.login.domain.User

interface UserDataSource {
    suspend fun login(username: String, password: String): Result<User>

    suspend fun signUp(user: User): Result<String>
}