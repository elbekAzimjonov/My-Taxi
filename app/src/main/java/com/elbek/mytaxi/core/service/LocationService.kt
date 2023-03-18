package com.elbek.mytaxi.core.service

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.elbek.mytaxi.core.notification.LocationNotification

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class LocationService : LifecycleService() {
    companion object {
        const val ACTION_START = "actionStart"
        const val ACTION_STOP = "actionStop"
    }

    @Inject
    lateinit var notification: LocationNotification

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        startForeground()
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun startForeground() {
        startForeground(LocationNotification.ID, notification.createNotification())
    }
}
