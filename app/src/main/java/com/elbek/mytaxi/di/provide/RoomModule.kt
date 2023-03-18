package com.elbek.mytaxi.di.provide

import android.app.Application
import androidx.room.Room
import com.elbek.mytaxi.data.local.constant.LocationDatabaseConst
import com.elbek.mytaxi.data.local.dao.LocationDao
import com.elbek.mytaxi.data.local.database.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(application: Application): LocationDatabase =
        Room.databaseBuilder(application, LocationDatabase::class.java, LocationDatabaseConst.NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(locationDatabase: LocationDatabase): LocationDao =
        locationDatabase.daoLocation()
}