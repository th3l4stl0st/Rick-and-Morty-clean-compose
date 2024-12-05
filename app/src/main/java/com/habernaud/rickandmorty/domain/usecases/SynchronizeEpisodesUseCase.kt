package com.habernaud.rickandmorty.domain.usecases

import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.repositories.EpisodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get
import retrofit2.HttpException
import java.net.UnknownHostException

class SynchronizeEpisodesUseCase(
    private val episodeRepository: EpisodeRepository = get(EpisodeRepository::class.java),
    private val ioDispatcher: CoroutineDispatcher = get(CoroutineDispatcher::class.java),
    private val resourcesHelper: ResourcesHelper = get(ResourcesHelper::class.java)
) {
    suspend operator fun invoke() : Resource<Unit> = withContext(ioDispatcher) {
        try {
            episodeRepository.synchronizeEpisodes()
            Resource.Success(Unit)
        }
        catch (e : UnknownHostException){
            Resource.Error(resourcesHelper.getString(R.string.error_network))
        }
        catch (e : HttpException){
            Resource.Error(resourcesHelper.getString(R.string.error_occured_code, e.code()))
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Error(resourcesHelper.getString(R.string.error_occured))
        }
    }
}