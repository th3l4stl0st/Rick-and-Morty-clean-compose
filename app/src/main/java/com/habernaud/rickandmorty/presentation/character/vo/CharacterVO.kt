package com.habernaud.rickandmorty.presentation.character.vo

import com.habernaud.rickandmorty.domain.models.Gender
import com.habernaud.rickandmorty.domain.models.Status

data class CharacterVO(
    val id : Long,
    val name : String,
    val status : Status = Status.Unknown,
    val species : String = "",
    val image : String,
    val gender : Gender? = null,
    val episodeList : List<EpisodeVO> = emptyList()
)