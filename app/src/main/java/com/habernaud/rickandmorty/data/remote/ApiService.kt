package com.habernaud.rickandmorty.data.remote

import com.habernaud.rickandmorty.data.remote.dto.BaseDtoDown
import com.habernaud.rickandmorty.data.remote.dto.CharacterDtoDown
import com.habernaud.rickandmorty.data.remote.dto.EpisodeDtoDown
import com.habernaud.rickandmorty.data.remote.dto.LocationDtoDown
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacterList(@Query("page") page : Int): BaseDtoDown<CharacterDtoDown>

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page : Int): BaseDtoDown<EpisodeDtoDown>

    @GET("location")
    suspend fun getLocations(@Query("page") page : Int): BaseDtoDown<LocationDtoDown>
}