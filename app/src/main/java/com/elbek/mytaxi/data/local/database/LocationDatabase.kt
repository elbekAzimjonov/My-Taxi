package com.elbek.mytaxi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elbek.mytaxi.data.local.constant.LocationDatabaseConst
import com.elbek.mytaxi.data.local.dao.LocationDao
import com.elbek.mytaxi.data.local.entity.LocationEntity

@Database(
    entities = [LocationEntity::class],
    version = LocationDatabaseConst.VERSION
)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun daoLocation(): LocationDao
}