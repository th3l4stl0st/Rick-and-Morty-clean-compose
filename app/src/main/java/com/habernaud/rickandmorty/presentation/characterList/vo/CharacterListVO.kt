package com.habernaud.rickandmorty.presentation.characterList.vo

import com.habernaud.rickandmorty.domain.models.Gender
import com.habernaud.rickandmorty.domain.models.Status

data class CharacterListVO(
    val id : Long,
    val name : String,
    val status : Status,
    val species : String,
    val image : String = "",
    val gender : Gender,
)