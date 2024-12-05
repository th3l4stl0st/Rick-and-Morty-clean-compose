package com.habernaud.rickandmorty.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.habernaud.rickandmorty.domain.models.Gender
import com.habernaud.rickandmorty.domain.models.Status

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val image: String,
    val gender: Gender,
    val url : String,
)