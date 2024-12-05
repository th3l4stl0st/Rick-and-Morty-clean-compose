package com.habernaud.rickandmorty.data.local.embeded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.habernaud.rickandmorty.data.local.entities.CharacterEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterEpisodeEntity
import com.habernaud.rickandmorty.data.local.entities.EpisodeEntity

data class EpisodeWithCharacterList(
    @Embedded val episode: EpisodeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CharacterEpisodeEntity::class,
            parentColumn = "episodeId",
            entityColumn = "characterId"
        )
    )
    val characterList: List<CharacterEntity>
)
