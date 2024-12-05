package com.habernaud.rickandmorty.domain.models

data class CharacterModel(
    val id : Long,
    val name : String,
    val status : Status = Status.Unknown,
    val species : String = "",
    val image : String,
    val gender : Gender = Gender.Unknown,
)