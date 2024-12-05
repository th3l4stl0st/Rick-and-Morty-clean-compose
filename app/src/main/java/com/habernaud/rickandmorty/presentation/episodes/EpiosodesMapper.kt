package com.habernaud.rickandmorty.presentation.episodes

import com.habernaud.rickandmorty.domain.models.EpisodeListModel
import com.habernaud.rickandmorty.presentation.episodes.vo.EpisodeListVO

class EpisodesMapper {
    fun mapToUI(episodeData: EpisodeListModel) = EpisodeListVO(
        id = episodeData.id,
        name = episodeData.name,
        airDate = episodeData.airDate,
        episode = episodeData.episode
    )

    fun mapToUIList(episodeList: List<EpisodeListModel>) = episodeList.map {
        mapToUI(it)
    }

    fun mapToUIGroupedList(groupedEpisodes: Map<String, List<EpisodeListModel>>): Map<String, List<EpisodeListVO>> {
        return groupedEpisodes.mapValues { (_, episodes) ->
            mapToUIList(episodes)
        }
    }
}
