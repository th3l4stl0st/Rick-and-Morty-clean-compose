package com.habernaud.rickandmorty.presentation.characterList

import com.habernaud.rickandmorty.domain.models.CharacterListModel

sealed class CharacterListEvent {
    data object LoadCharacterList : CharacterListEvent()

    data class OnQueryChanged(val query : String) : CharacterListEvent()
}