package com.habernaud.rickandmorty.domain.usecases

import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import com.habernaud.rickandmorty.domain.models.EpisodeListModel
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.repositories.EpisodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get

class GetGroupedEpisodeListUseCase(
    private val episodeRepository: EpisodeRepository = get(EpisodeRepository::class.java),
    private val resourcesHelper: ResourcesHelper = get(ResourcesHelper::class.java),
    private val ioDispatcher: CoroutineDispatcher = get(CoroutineDispatcher::class.java)
) {
    suspend operator fun invoke() : Resource<Map<String, List<EpisodeListModel>>> = withContext(ioDispatcher) {
        try {
            val episodes = episodeRepository.getEpisodeList()

            val result = episodes.groupBy { it.episode.substringBefore("E").replace("S", "") }

            Resource.Success(result)
        }
        catch (e : Exception){
            Resource.Error(resourcesHelper.getString(R.string.error_occured))
        }
    }
}