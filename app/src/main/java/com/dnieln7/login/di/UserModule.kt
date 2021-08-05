package com.dnieln7.login.di

import com.dnieln7.login.data.source.user.UserDataSource
import com.dnieln7.login.data.source.user.UserInMemoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
annotation class InMemoryDataSource

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserInMemoryModule {

    @Binds
    @ViewModelScoped
    @InMemoryDataSource
    abstract fun bindUserInMemoryDataSource(userInMemoryDataSource: UserInMemoryDataSource): UserDataSource
}