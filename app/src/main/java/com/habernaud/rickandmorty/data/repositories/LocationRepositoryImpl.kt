package com.habernaud.rickandmorty.data.repositories

import com.habernaud.rickandmorty.commons.helpers.PreferencesHelper
import com.habernaud.rickandmorty.data.local.dao.LocationDao
import com.habernaud.rickandmorty.data.local.entities.LocationEntity
import com.habernaud.rickandmorty.data.remote.ApiService
import com.habernaud.rickandmorty.domain.repositories.LocationRepository
import org.koin.java.KoinJavaComponent.get

class LocationRepositoryImpl(
    private val apiService: ApiService = get(ApiService::class.java),
    private val locationDao: LocationDao = get(LocationDao::class.java),
    private val preferencesHelper: PreferencesHelper = get(PreferencesHelper::class.java)
) : LocationRepository {
    override suspend fun synchronizeLocations() {

        if (preferencesHelper.locationsSynchronizationDone)
            return

        locationDao.nuke()

        var page = 1
        var again = true

        while (again){
            val locations = apiService.getLocations(page++)
            again = locations.info.next != null

            val locationsEntity = locations.results.map { location ->
                LocationEntity(
                    id = location.id,
                    name = location.name,
                    type = location.type,
                    dimension = location.dimension,
                    url = location.url
                )
            }
            locationDao.insert(locationsEntity)
        }

        preferencesHelper.locationsSynchronizationDone = true
    }
}