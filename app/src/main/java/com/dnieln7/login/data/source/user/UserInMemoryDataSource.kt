package com.dnieln7.login.data.source.user

import com.dnieln7.login.domain.Result
import com.dnieln7.login.domain.User

class UserInMemoryDataSource : UserDataSource {
    private val users: MutableList<User> = mutableListOf(
        User(
            name = "Daniel",
            lastName = "Nolasco",
            username = "dnieln7",
            email = "dnieln7@gmail.com",
            password = "admin",
        )
    )


    override suspend fun login(username: String, password: String): Result<User> {
        val user = users.find { it.username == username }

        return if (user == null) {
            Result.Error(reason = "User not found")
        } else {
            if (user.password == password) {
                Result.Success(data = user)
            } else {
                Result.Error(reason = "Wrong password")
            }
        }
    }

    override suspend fun signUp(user: User): Result<String> {
        val existing = users.find { it.username == user.username }

        if (existing != null) {
            return Result.Error(reason = "Username already exits")
        }

        users.add(user)

        return Result.Success("OK")
    }
}