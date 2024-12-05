package com.habernaud.rickandmorty.commons.extensions

import android.util.Log
import androidx.navigation.NavBackStackEntry
import com.habernaud.rickandmorty.commons.helpers.ResourcesHelper
import org.koin.java.KoinJavaComponent.get
import com.habernaud.rickandmorty.R

private val resourcesHelper : ResourcesHelper = get(ResourcesHelper::class.java)

fun NavBackStackEntry.getTitle() : String {
    Log.d("Character", "getTitle: ${this.destination.route?.substringAfterLast(".")}")
    return when(this.destination.route?.substringAfterLast(".")){
        "CharacterList" -> resourcesHelper.getString(R.string.characterList)
        "Character/{id}" -> resourcesHelper.getString(R.string.characterList)
        "Episodes" -> resourcesHelper.getString(R.string.episodes)
        "Locations" -> resourcesHelper.getString(R.string.locations)
        else -> ""
    }
}

fun NavBackStackEntry.getSubtitle() : String {
    Log.d("Character", "getSubtitle: ${this.arguments?.getLong("id").toString() ?: ""}")
    return when(this.destination.route?.substringAfterLast(".")){
        "Character/{id}" -> {
            val characterName = this.arguments?.getLong("id").toString() ?: ""
            characterName
        }
        else -> ""
    }
}