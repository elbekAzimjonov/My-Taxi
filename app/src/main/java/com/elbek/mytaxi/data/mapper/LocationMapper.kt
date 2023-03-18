package com.elbek.mytaxi.data.mapper

import com.elbek.mytaxi.data.local.entity.LocationEntity
import com.elbek.mytaxi.data.model.LocationModel

fun LocationModel.toEntity(): LocationEntity {
    return LocationEntity(
        _id = id,
        latitude = latitude,
        longitude = longitude
    )
}