package com.elbek.mytaxi.di.app

import android.app.Application
import com.elbek.mytaxi.core.notification.LocationNotification
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
@HiltAndroidApp
class App:Application() {
    @Inject
    lateinit var notification:   LocationNotification
    override fun onCreate() {
        super.onCreate()
        notification.createChannel()
    }
}