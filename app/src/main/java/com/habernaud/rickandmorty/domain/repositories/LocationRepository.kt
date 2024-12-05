package com.habernaud.rickandmorty.domain.repositories

interface LocationRepository {
    suspend fun synchronizeLocations()
}