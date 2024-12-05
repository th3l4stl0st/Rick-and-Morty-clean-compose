package com.habernaud.rickandmorty.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.habernaud.rickandmorty.domain.models.Gender
import com.habernaud.rickandmorty.domain.models.Status

data class CharacterDtoDown(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    val status: Status,

    @SerializedName("species")
    val species: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("gender")
    val gender : Gender,

    @SerializedName("image")
    val image : String,

    @SerializedName("episode")
    val episode : List<String>,

    @SerializedName("url")
    val url : String,

    @SerializedName("origin")
    val origin : Location?,

    @SerializedName("location")
    val location : Location?,
){
    data class Location(
        @SerializedName("name")
        val name: String,

        @SerializedName("url")
        val url: String,
    )
}