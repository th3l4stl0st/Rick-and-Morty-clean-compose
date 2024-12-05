package com.habernaud.rickandmorty.presentation.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habernaud.rickandmorty.commons.helpers.ErrorHelper
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.usecases.GetCharacterEpisodesUseCase
import com.habernaud.rickandmorty.domain.usecases.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.Long

class CharacterViewModel(
    private val getCharacterUseCase: GetCharacterUseCase ,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase ,
    private val characterMapper: CharacterMapper ,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    fun onEvent(event: CharacterEvent) {
        when (event) {
            is CharacterEvent.LoadCharacter -> {
                _uiState.value = _uiState.value.copy(status = CharacterScreenStatus.Loading)
                loadData(event.id)
            }
        }
    }
    private fun loadData(id: Long) {
        viewModelScope.launch {
            val characterJob = getCharacterUseCase(id)
            val episodesJob = getCharacterEpisodesUseCase(id)
            combine(characterJob, episodesJob) { character, episodes ->
                if (character is Resource.Success && episodes is Resource.Success) {
                    _uiState.update {
                        it.copy(
                            status = CharacterScreenStatus.Success(
                               characterMapper.mapToUI(character.data, episodes.data)
                            )
                        )
                    }
                }else{
                    _uiState.update {
                        it.copy(
                            status = CharacterScreenStatus.Error(
                                "error"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}