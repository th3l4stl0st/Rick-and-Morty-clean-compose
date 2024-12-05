package com.habernaud.rickandmorty.domain.models

import java.util.Date

data class EpisodeModel(
    val id : Long,
    val name : String,
    val airDate : Date,
    val episode : String,
    val characterList : List<CharacterModel>
)