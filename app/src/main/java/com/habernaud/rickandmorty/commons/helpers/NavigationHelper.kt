package com.habernaud.rickandmorty.commons.helpers

import kotlinx.coroutines.flow.SharedFlow

interface NavigationHelper {
    val sharedFlow : SharedFlow<NavigationEvent>

    fun navigateTo(route: Destination, popupTo : String? = null)

    fun goBack()

    sealed class NavigationEvent {
        data class NavigateTo(val destination: Destination, val popupTo : String? = null) : NavigationEvent()
        data object GoBack : NavigationEvent()
    }


    sealed class Destination(val route: String) {
        data object CharacterList : Destination("characterList")
        data class Character(val id : Long, val name : String) : Destination("characterList/$id?name=$name")
    }
}
