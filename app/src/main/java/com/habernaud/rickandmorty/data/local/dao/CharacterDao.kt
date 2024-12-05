package com.habernaud.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.habernaud.rickandmorty.data.local.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterentity WHERE id = :id")
    suspend fun getCharacter(id: Long): CharacterEntity

    @Query("SELECT * FROM characterentity")
    suspend fun getCharacterList(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(characterList: List<CharacterEntity>)

    @Query("DELETE FROM characterentity")
    suspend fun nuke()
}