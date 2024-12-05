package com.habernaud.rickandmorty.domain.usecases

import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import com.habernaud.rickandmorty.domain.models.CharacterModel
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.repositories.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get

class GetCharacterUseCase(
    private val characterRepository: CharacterRepository = get(CharacterRepository::class.java),
    private val ioDispatcher: CoroutineDispatcher = get(CoroutineDispatcher::class.java),
    private val resourcesHelper: ResourcesHelper = get(ResourcesHelper::class.java)
) {
    suspend operator fun invoke(id : Long) : Flow<Resource<CharacterModel>> = flowOf(
        try {
            val character = characterRepository.getCharacter(id)
            Resource.Success(character)
        }
        catch (e : NullPointerException){
            Resource.Error(resourcesHelper.getString(R.string.error_character_not_found))
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Error(resourcesHelper.getString(R.string.error_occured))
        }
    )
}