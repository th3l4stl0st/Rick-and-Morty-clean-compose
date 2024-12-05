package com.habernaud.rickandmorty.data.repositories

import com.habernaud.rickandmorty.commons.helpers.PreferencesHelper
import com.habernaud.rickandmorty.data.local.dao.CharacterDao
import com.habernaud.rickandmorty.data.local.dao.CharacterEpisodeDao
import com.habernaud.rickandmorty.data.local.dao.CharacterLocationDao
import com.habernaud.rickandmorty.data.local.dao.CharacterOriginDao
import com.habernaud.rickandmorty.data.local.entities.CharacterEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterEpisodeEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterLocationEntity
import com.habernaud.rickandmorty.data.local.entities.CharacterOriginEntity
import com.habernaud.rickandmorty.data.remote.ApiService
import com.habernaud.rickandmorty.domain.models.CharacterListModel
import com.habernaud.rickandmorty.domain.models.CharacterModel
import com.habernaud.rickandmorty.domain.repositories.CharacterRepository
import org.koin.java.KoinJavaComponent.get

class CharacterRepositoryImpl(
    private val apiService: ApiService = get(ApiService::class.java),
    private val characterDao: CharacterDao = get(CharacterDao::class.java),
    private val characterEpisodeDao: CharacterEpisodeDao = get(CharacterEpisodeDao::class.java),
    private val characterLocationDao: CharacterLocationDao = get(CharacterLocationDao::class.java),
    private val characterOriginDao: CharacterOriginDao = get(CharacterOriginDao::class.java),
    private val preferencesHelper: PreferencesHelper = get(PreferencesHelper::class.java),
) : CharacterRepository {

    override suspend fun synchronizeCharacterList() {

        if (preferencesHelper.characterListSynchronizationDone)
            return

        characterDao.nuke()

        var page = 1
        var again = true

        while (again){
            val characterListDto = apiService.getCharacterList(page++)
            again = characterListDto.info.next != null

            val characterListEntity = characterListDto.results.map { character ->
                CharacterEntity(
                    id = character.id,
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    type = character.type,
                    gender = character.gender,
                    url = character.url,
                    image = character.image
                )
            }

            characterDao.insertOrUpdate(characterListEntity)


            characterListDto.results.forEach { characterDto ->
                characterDto.episode.forEach {  episodeUrl ->
                    val episodeId = episodeUrl.substringAfterLast("/").toLong()

                    val characterEpisode =
                        CharacterEpisodeEntity(
                            characterId = characterDto.id,
                            episodeId = episodeId
                        )
                    
                    characterEpisodeDao.insertCharacterEpisode(characterEpisode)
                }

                characterDto.location?.url?.substringAfterLast("/")?.toLongOrNull()?.let {  locationId ->
                    val characterLocation =
                        CharacterLocationEntity(
                            characterId = characterDto.id,
                            locationId = locationId
                        )
                    characterLocationDao.insertCharacterLocation(characterLocation)
                }

                characterDto.origin?.url?.substringAfterLast("/")?.toLongOrNull()?.let { locationId ->
                    val characterOrigin =
                        CharacterOriginEntity(
                            characterId = characterDto.id,
                            locationId = locationId
                        )
                    characterOriginDao.insertCharacterOrigin(characterOrigin)
                }
            }
        }

        preferencesHelper.characterListSynchronizationDone = true
    }

    override suspend fun getCharacterList(): List<CharacterListModel> {

        val characterListDb = characterDao.getCharacterList()

        return characterListDb.map { entity ->
            CharacterListModel(
                id = entity.id,
                name = entity.name,
                status = entity.status,
                species = entity.species,
                gender = entity.gender,
                image = entity.image
            )
        }
    }

    override suspend fun getCharacter(id: Long): CharacterModel {
        val character = characterDao.getCharacter(id)
        return CharacterModel(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            image = character.image,
            gender = character.gender
        )

    }
}