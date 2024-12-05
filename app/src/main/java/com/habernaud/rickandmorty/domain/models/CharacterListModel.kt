package com.habernaud.rickandmorty.domain.models

data class CharacterListModel(
    val id : Long,
    val name : String,
    val status : Status,
    val species : String,
    val gender : Gender,
    val image : String
)