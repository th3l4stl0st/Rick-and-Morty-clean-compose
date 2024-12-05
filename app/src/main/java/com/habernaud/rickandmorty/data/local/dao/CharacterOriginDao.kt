package com.habernaud.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.habernaud.rickandmorty.data.local.entities.CharacterOriginEntity

@Dao
interface CharacterOriginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterOrigin(characterOriginEntity: CharacterOriginEntity)
}