package com.habernaud.rickandmorty.domain.repositories

import com.habernaud.rickandmorty.domain.models.EpisodeListModel
import com.habernaud.rickandmorty.domain.models.EpisodeModel

interface EpisodeRepository {

    suspend fun synchronizeEpisodes()

    suspend fun getEpisodesForCharacter(characterId : Long): List<EpisodeModel>

    suspend fun getEpisodeList(): List<EpisodeListModel>
}