package com.habernaud.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.habernaud.rickandmorty.data.local.entities.CharacterEpisodeEntity

@Dao
interface CharacterEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterEpisode(characterEpisodeEntity: CharacterEpisodeEntity)
}