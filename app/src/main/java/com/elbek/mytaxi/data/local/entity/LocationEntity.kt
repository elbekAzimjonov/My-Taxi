package com.elbek.mytaxi.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elbek.mytaxi.data.local.constant.LocationDatabaseConst

@Entity(tableName = LocationDatabaseConst.TABLE_LOCATION)
data class LocationEntity(

    @PrimaryKey(autoGenerate = true)
    val _id: Long?,

    @ColumnInfo val latitude: Double,
    @ColumnInfo val longitude: Double,
)
