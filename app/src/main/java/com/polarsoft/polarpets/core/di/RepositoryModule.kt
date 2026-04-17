package com.polarsoft.polarpets.core.di

import com.polarsoft.polarpets.features.Login.data.repository.LoginRepositoryImpl
import com.polarsoft.polarpets.features.Login.domain.repository.LoginRepository
import com.polarsoft.polarpets.features.Tienda.data.repository.TiendaRepositoryImpl
import com.polarsoft.polarpets.features.Tienda.domain.repository.TiendaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindTiendaRepository(
        impl: TiendaRepositoryImpl
    ): TiendaRepository
}
