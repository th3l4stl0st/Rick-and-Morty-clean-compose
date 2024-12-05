package com.habernaud.rickandmorty.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
object CharacterList

@Serializable
data class Character(val id: Long)


@Serializable
object Episodes

@Serializable
object Locations

//TopLevel Routes
data class TopLevelRoute<T : Any>(
    val route: T,
    @StringRes val label : Int,
    @DrawableRes val icon : Int
)