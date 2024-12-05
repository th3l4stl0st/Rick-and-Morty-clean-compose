package com.habernaud.rickandmorty.presentation.characterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habernaud.rickandmorty.R
import com.habernaud.rickandmorty.commons.helpers.ErrorHelper
import com.habernaud.rickandmorty.commons.helpers.NavigationHelper
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import com.habernaud.rickandmorty.domain.models.Resource
import com.habernaud.rickandmorty.domain.usecases.GetCharacterListUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeCharacterListUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeEpisodesUseCase
import com.habernaud.rickandmorty.domain.usecases.SynchronizeLocationsUseCase
import com.habernaud.rickandmorty.presentation.characterList.vo.CharacterListVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CharacterListViewModel(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val synchronizeEpisodesUseCase: SynchronizeEpisodesUseCase,
    private val synchronizeLocationsUseCase : SynchronizeLocationsUseCase,
    private val characterListMapper: CharacterListMapper,
    private val errorHelper: ErrorHelper,
    private val resourcesHelper: ResourcesHelper,
    private val synchronizeCharacterListUseCase: SynchronizeCharacterListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUiState())
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    fun onEvent(event : CharacterListEvent){
        when(event){
            is CharacterListEvent.LoadCharacterList -> loadEpisodes()

            is CharacterListEvent.OnQueryChanged -> {
                _uiState.update { it.copy(query = event.query) }
            }
        }
    }

    private fun loadEpisodes(){
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, loadingMessage = resourcesHelper.getString(R.string.loading_episodes)) }

            when(val result = synchronizeEpisodesUseCase()){
                is Resource.Success -> {
                    _uiState.update { it.copy(loadingMessage = resourcesHelper.getString(R.string.loading_locations)) }
                    loadLocations()
                }
                is Resource.Error -> {
                    errorHelper.showError(result.message)
                    _uiState.update { it.copy(loading = false) }
                }
            }
        }
    }

    private suspend fun loadLocations(){
        when(val result = synchronizeLocationsUseCase()){
            is Resource.Success -> {
                _uiState.update { it.copy(loadingMessage = resourcesHelper.getString(R.string.loading_characterList)) }
                loadCharacterList()
            }
            is Resource.Error -> {
                errorHelper.showError(result.message)
                _uiState.update { it.copy(loading = false) }
            }
        }
    }

    private suspend fun loadCharacterList(){
        when(val result = synchronizeCharacterListUseCase()){
            is Resource.Success -> {
                getCharacterList()
            }
            is Resource.Error -> {
                errorHelper.showError(result.message)
                _uiState.update { it.copy(loading = false) }
            }
        }
    }

    private suspend fun getCharacterList(){
        when(val result = getCharacterListUseCase()){
            is Resource.Success -> {
                _uiState.update { it.copy(loading = false, characterList = characterListMapper.mapToUIList(result.data)) }
            }
            is Resource.Error -> {
                errorHelper.showError(result.message)
                _uiState.update { it.copy(loading = false) }
            }
        }
    }
}