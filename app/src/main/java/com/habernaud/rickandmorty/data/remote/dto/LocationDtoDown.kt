package com.habernaud.rickandmorty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationDtoDown(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("dimension")
    val dimension: String,

    @SerializedName("residents")
    val residents: List<String>,

    @SerializedName("url")
    val url: String,

    @SerializedName("created")
    val created: String
)
