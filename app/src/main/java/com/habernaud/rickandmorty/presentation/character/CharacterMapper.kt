package com.habernaud.rickandmorty.presentation.character

import com.habernaud.rickandmorty.domain.models.CharacterModel
import com.habernaud.rickandmorty.domain.models.EpisodeModel
import com.habernaud.rickandmorty.presentation.character.vo.CharacterVO
import com.habernaud.rickandmorty.presentation.character.vo.EpisodeVO

class CharacterMapper{
    fun mapToUI(characterData : CharacterModel, episodeList : List<EpisodeModel>) = CharacterVO(
        id = characterData.id,
        name = characterData.name,
        status = characterData.status,
        species = characterData.species,
        image = characterData.image,
        gender = characterData.gender,
        episodeList = episodeList.map{
            EpisodeVO(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode,
                characterList = it.characterList.map{
                    CharacterVO(
                        id = it.id,
                        name = it.name,
                        image = it.image
                    )
                }
            )
        }
    )
}