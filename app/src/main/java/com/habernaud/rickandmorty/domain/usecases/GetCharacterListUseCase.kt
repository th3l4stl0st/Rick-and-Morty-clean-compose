package com.habernaud.rickandmorty.domain.usecases

import com.habernaud.rickandmorty.domain.models.CharacterListModel
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.repositories.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get
import retrofit2.HttpException
import java.net.UnknownHostException

class GetCharacterListUseCase(
    private val characterRepository: CharacterRepository = get(CharacterRepository::class.java),
    private val ioDispatcher: CoroutineDispatcher = get(CoroutineDispatcher::class.java),
) {
    suspend operator fun invoke() : Resource<List<CharacterListModel>> = withContext(ioDispatcher) {
        try {
            val characterList = characterRepository.getCharacterList()
            Resource.Success(characterList)
        }
        catch (e : UnknownHostException){
            Resource.Error("Network error")
        }
        catch (e : HttpException){
            Resource.Error("Error ${e.code()}")
        }
        catch (e : Exception){
            Resource.Error("Unknown error")
        }
    }
}