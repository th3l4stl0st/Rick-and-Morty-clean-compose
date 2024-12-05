package com.habernaud.rickandmorty.presentation.characterList

import com.habernaud.rickandmorty.presentation.characterList.vo.CharacterListVO

data class CharacterListUiState(
    val loading : Boolean = false,
    val loadingMessage : String = "",

    val characterList : List<CharacterListVO> = emptyList(),

    val query : String = ""
)