package com.elbek.mytaxi.di.bind

import com.elbek.mytaxi.core.util.AndroidLocationProvider
import com.elbek.mytaxi.core.util.LocationProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    @Singleton
    abstract fun bindLocationProvider(androidLocationProvider: AndroidLocationProvider): LocationProvider
}