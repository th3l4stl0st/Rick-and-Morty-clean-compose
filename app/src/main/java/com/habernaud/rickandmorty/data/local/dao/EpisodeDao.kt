package com.habernaud.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.habernaud.rickandmorty.data.local.entities.CharacterEntity
import com.habernaud.rickandmorty.data.local.entities.EpisodeEntity

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM EpisodeEntity")
    suspend fun getEpisodeList(): List<EpisodeEntity>

    @Query("SELECT * FROM EpisodeEntity WHERE id IN (SELECT episodeId FROM CharacterEpisodeEntity WHERE characterId = :characterId)")
    suspend fun getEpisodesForCharacter(characterId: Long): List<EpisodeEntity>


    @Query("SELECT * FROM CharacterEntity WHERE id IN (SELECT characterId FROM CharacterEpisodeEntity WHERE episodeId = :episodeId)")
    suspend fun getCharacterListForEpisode(episodeId: Long): List<CharacterEntity>

    @Insert
    suspend fun insert(episodesEntity: List<EpisodeEntity>)

    @Query("DELETE FROM episodeentity")
    suspend fun nuke()
}