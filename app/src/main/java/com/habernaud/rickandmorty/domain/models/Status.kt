package com.habernaud.rickandmorty.domain.models

import com.google.gson.annotations.SerializedName

enum class Status {
    Alive,
    Dead,
    @SerializedName("unknown")
    Unknown
}