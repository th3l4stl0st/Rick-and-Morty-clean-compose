package com.habernaud.rickandmorty.presentation.episodes

import com.habernaud.rickandmorty.presentation.episodes.vo.EpisodeListVO

data class EpisodesUiState(
    val loading : Boolean = false,

    val episodes : Map<String, List<EpisodeListVO>> = emptyMap()
)