package com.habernaud.rickandmorty.presentation.character.vo

import java.util.Date

data class EpisodeVO(
    val id : Long,
    val name : String,
    val airDate : Date,
    val episode : String,
    val characterList : List<CharacterVO>
)