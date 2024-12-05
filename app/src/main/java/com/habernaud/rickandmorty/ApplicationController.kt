package com.habernaud.rickandmorty

import android.app.Application
import androidx.room.Room
import com.habernaud.rickandmorty.commons.helpers.ErrorHelper
import com.habernaud.rickandmorty.commons.helpers.NavigationHelper
import com.habernaud.rickandmorty.commons.helpers.PreferencesHelper
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import com.habernaud.rickandmorty.commons.helpers.implementations.ErrorHelperImpl
import com.habernaud.rickandmorty.commons.helpers.implementations.NavigationHelperImpl
import com.habernaud.rickandmorty.commons.helpers.implementations.PreferencesHelperImpl
import com.habernaud.rickandmorty.commons.helpers.implementations.ResourcesHelperImpl
import com.habernaud.rickandmorty.data.local.ApplicationDatabase
import com.habernaud.rickandmorty.data.remote.ApiClient
import com.habernaud.rickandmorty.data.repositories.CharacterRepositoryImpl
import com.habernaud.rickandmorty.data.repositories.EpisodeRepositoryImpl
import com.habernaud.rickandmorty.data.repositories.LocationRepositoryImpl
import com.habernaud.rickandmorty.domain.repositories.CharacterRepository
import com.habernaud.rickandmorty.domain.repositories.EpisodeRepository
import com.habernaud.rickandmorty.domain.repositories.LocationRepository
import com.habernaud.rickandmorty.domain.usecases.GetCharacterEpisodesUseCase
import com.habernaud.rickandmorty.domain.usecases.GetCharacterListUseCase
import com.habernaud.rickandmorty.domain.usecases.GetCharacterUseCase
import com.habernaud.rickandmorty.domain.usecases.GetGroupedEpisodeListUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeCharacterListUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeEpisodesUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeLocationsUseCase
import com.habernaud.rickandmorty.presentation.character.CharacterMapper
import com.habernaud.rickandmorty.presentation.character.CharacterViewModel
import com.habernaud.rickandmorty.presentation.characterList.CharacterListMapper
import com.habernaud.rickandmorty.presentation.characterList.CharacterListViewModel
import com.habernaud.rickandmorty.presentation.episodes.EpisodesMapper
import com.habernaud.rickandmorty.presentation.episodes.EpisodesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

class ApplicationController : Application() {

    private val database: ApplicationDatabase by lazy {
        Room.databaseBuilder(this, ApplicationDatabase::class.java, "rickandmorty")
            .fallbackToDestructiveMigration() // If migrations needed delete all data and clear schema
            .build()
    }


    private val applicationModule = module {
        //ViewModels
        viewModelOf(::CharacterListViewModel)
        viewModelOf(::CharacterViewModel)
        viewModelOf(::EpisodesViewModel)

        // Helpers
        singleOf(::ResourcesHelperImpl) bind ResourcesHelper::class
        singleOf(::NavigationHelperImpl) bind NavigationHelper::class
        singleOf(::ErrorHelperImpl) bind ErrorHelper::class
        singleOf(::PreferencesHelperImpl) bind PreferencesHelper::class

        // UseCases
        singleOf(::GetCharacterListUseCase)
        singleOf(::GetCharacterUseCase)
        singleOf(::GetCharacterEpisodesUseCase)
        singleOf(::SynchronizeEpisodesUseCase)
        singleOf(::SynchronizeCharacterListUseCase)
        singleOf(::SynchronizeLocationsUseCase)
        singleOf(::GetGroupedEpisodeListUseCase)

        // Repositories
        singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class
        singleOf(::EpisodeRepositoryImpl) bind EpisodeRepository::class
        singleOf(::LocationRepositoryImpl) bind LocationRepository::class

        // Dao
        single { database.characterDao() }
        single { database.episodeDao() }
        single { database.characterEpisodeDao() }
        single { database.characterEpisodeDao() }
        single { database.locationDao() }
        single { database.characterLocationDao() }
        single { database.characterOriginDao() }

        // Remote
        single { ApiClient.apiService }

        // https://developer.android.com/kotlin/coroutines/coroutines-best-practices?hl=fr
        single<CoroutineDispatcher> { Dispatchers.IO }

        //Mappers
        singleOf(::CharacterMapper)
        singleOf(::CharacterListMapper)
        singleOf(::EpisodesMapper)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(applicationModule)
            androidContext(this@ApplicationController)
        }
    }
}