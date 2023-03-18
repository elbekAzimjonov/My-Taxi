package com.elbek.mytaxi.di.provide

import android.app.Application
import android.app.NotificationManager
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.elbek.mytaxi.core.notification.LocationNotification
import com.elbek.mytaxi.core.util.AndroidLocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.M)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideLocationManager(application: Application): LocationManager =
        application.getSystemService(LocationManager::class.java)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun provideAndroidLocationProvider(
        application: Application,
        locationManager: LocationManager,
        fusedLocationProviderClient: FusedLocationProviderClient,
    ): AndroidLocationProvider = AndroidLocationProvider(
        context = application,
        manager = locationManager,
        client = fusedLocationProviderClient
    )

    @Provides
    @Singleton
    fun provideNotificationManager(application: Application): NotificationManager =
        application.getSystemService(NotificationManager::class.java)

    @Provides
    @Singleton
    fun provideLocationNotification(
        application: Application,
        notificationManager: NotificationManager,
    ): LocationNotification = LocationNotification(
        context = application,
        manager = notificationManager
    )

}