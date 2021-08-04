package com.dnieln7.login

import android.app.Application

class LoginApplication: Application() {
    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()

        serviceLocator = ServiceLocator(applicationContext)
    }
}