package com.elbek.mytaxi.presentation.splash

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.elbek.mytaxi.R
import com.elbek.mytaxi.core.service.LocationService
import com.elbek.mytaxi.presentation.activity.MainActivity

class Splash : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)

    }
}