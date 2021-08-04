package com.dnieln7.login.domain

data class User(
    val name: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
)