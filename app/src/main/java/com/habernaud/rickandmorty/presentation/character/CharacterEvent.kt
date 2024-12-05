package com.habernaud.rickandmorty.presentation.character

sealed class CharacterEvent {
    data class LoadCharacter(val id: Long) : CharacterEvent()
}