package com.habernaud.rickandmorty.presentation.character

import com.habernaud.rickandmorty.presentation.character.vo.CharacterVO

data class CharacterUiState(
    val status: CharacterScreenStatus = CharacterScreenStatus.Idle,
)
sealed interface CharacterScreenStatus{
    data object Idle : CharacterScreenStatus
    data object Loading : CharacterScreenStatus
    data class Success(val character: CharacterVO) : CharacterScreenStatus
    data class Error(val message: String) : CharacterScreenStatus
}