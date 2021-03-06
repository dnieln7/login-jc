package com.dnieln7.login.navigation.auth

enum class Destinations {
    Login,
    SignUp;

    companion object {
        fun getRouteName(route: String?): Destinations {
            return when (route?.substringBefore("/")) {
                Login.name -> Login
                SignUp.name -> SignUp
                null -> Login
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
        }
    }
}