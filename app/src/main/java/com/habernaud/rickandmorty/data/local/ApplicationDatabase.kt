package com.habernaud.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.habernaud.rickandmorty.data.local.dao.CharacterDao
import com.habernaud.rickandmorty.data.local.dao.CharacterEpisodeDao
import com.habernaud.rickandmorty.data.local.dao.CharacterLocationDao
import com.habernaud.rickandmorty.data.local.dao.CharacterOriginDao
import com.habernaud.rickandmorty.data.local.dao.EpisodeDao
import com.habernaud.rickandmorty.data.local.dao.LocationDao
import com.habernaud.rickandmorty.data.local.entities.CharacterEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterEpisodeEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterLocationEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterOriginEntity
import com.habernaud.rickandmorty.data.local.entities.EpisodeEntity
import com.habernaud.rickandmorty.data.local.entities.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        CharacterEpisodeEntity::class,
        LocationEntity::class,
        CharacterLocationEntity::class,
        CharacterOriginEntity::class
    ],
    version = 1,

    )
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    abstract fun episodeDao(): EpisodeDao
    abstract fun characterEpisodeDao(): CharacterEpisodeDao

    abstract fun locationDao(): LocationDao
    abstract fun characterLocationDao(): CharacterLocationDao
    abstract fun characterOriginDao(): CharacterOriginDao
}