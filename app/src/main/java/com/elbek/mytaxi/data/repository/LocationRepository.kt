package com.elbek.mytaxi.data.repository

import com.elbek.mytaxi.data.local.dao.LocationDao
import com.elbek.mytaxi.data.mapper.toEntity
import com.elbek.mytaxi.data.model.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
) {
    // UPDATE
    suspend fun updateLocation(location: LocationModel) {
        withContext(Dispatchers.IO) {
            locationDao.updateLocation(location.toEntity())
        }
    }
}