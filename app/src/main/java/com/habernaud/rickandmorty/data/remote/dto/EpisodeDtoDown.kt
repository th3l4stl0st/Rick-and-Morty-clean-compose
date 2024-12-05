package com.habernaud.rickandmorty.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EpisodeDtoDown(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("air_date")
    val airDate: Date,

    @SerializedName("episode")
    val episode: String,

    @SerializedName("characterList")
    val characterList: List<String>,

    @SerializedName("url")
    val url: String,

    @SerializedName("created")
    val created: String
)

