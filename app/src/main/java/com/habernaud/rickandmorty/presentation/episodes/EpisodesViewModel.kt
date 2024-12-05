package com.habernaud.rickandmorty.presentation.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.usecases.GetGroupedEpisodeListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val getGroupedEpisodeListUseCase: GetGroupedEpisodeListUseCase,
    private val episodesMapper: EpisodesMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodesUiState())
    val uiState: StateFlow<EpisodesUiState> = _uiState.asStateFlow()


    fun onEvent(event : EpisodesEvent){
        when(event){
            EpisodesEvent.OnLoadEpisodes -> {
                onLoadEpisodes()
            }
        }
    }


    private fun onLoadEpisodes(){
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true)}
            when(val result = getGroupedEpisodeListUseCase()){
                is Resource.Success -> {
                    _uiState.update { it.copy(episodes = episodesMapper.mapToUIGroupedList(result.data), loading = false)}
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(loading = false)}
                }
            }
        }
    }
}