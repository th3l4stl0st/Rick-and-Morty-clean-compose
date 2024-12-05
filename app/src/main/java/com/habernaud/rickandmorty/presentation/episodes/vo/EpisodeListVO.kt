package com.habernaud.rickandmorty.presentation.episodes.vo

import java.util.Date

data class EpisodeListVO(
    val id: Long,
    val name: String,
    val airDate: Date,
    val episode: String
)