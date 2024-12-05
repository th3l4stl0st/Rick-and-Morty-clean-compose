package com.habernaud.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.habernaud.rickandmorty.data.local.entities.LocationEntity

@Dao
interface LocationDao {
    @Query("DELETE FROM locationentity")
    suspend fun nuke()

    @Insert
    suspend fun insert(locationsEntity: List<LocationEntity>)
}