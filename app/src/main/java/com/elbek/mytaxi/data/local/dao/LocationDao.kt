package com.elbek.mytaxi.data.local.dao

import androidx.room.*
import com.elbek.mytaxi.data.local.constant.LocationDatabaseConst.TABLE_LOCATION
import com.elbek.mytaxi.data.local.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    // UPDATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLocation(location: LocationEntity)

    // DELETE ALL
    @Query("DELETE FROM $TABLE_LOCATION")
    suspend fun deleteAllLocation()

    //GET ALL
    @Query("SELECT * FROM $TABLE_LOCATION ORDER BY _id DESC")
    fun getAllLocation(): Flow<List<LocationEntity>>

}