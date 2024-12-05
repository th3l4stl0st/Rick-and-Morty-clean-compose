package com.habernaud.rickandmorty.presentation.episodes

sealed class EpisodesEvent {
    data object OnLoadEpisodes : EpisodesEvent()
}