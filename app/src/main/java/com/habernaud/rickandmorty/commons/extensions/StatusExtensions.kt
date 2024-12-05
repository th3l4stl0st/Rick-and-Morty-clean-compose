package com.habernaud.rickandmorty.commons.extensions

import androidx.compose.ui.graphics.Color
import com.habernaud.rickandmorty.R
import org.koin.java.KoinJavaComponent.get

private val resourcesHelper : com.habernaud.rickandmorty.commons.helpers.ResourcesHelper = get(
    com.habernaud.rickandmorty.commons.helpers.ResourcesHelper::class.java)

fun com.habernaud.rickandmorty.domain.models.Status.toText() : String =
    when(this){
        com.habernaud.rickandmorty.domain.models.Status.Alive -> resourcesHelper.getString(R.string.status_alive)
        com.habernaud.rickandmorty.domain.models.Status.Dead -> resourcesHelper.getString(R.string.status_dead)
        com.habernaud.rickandmorty.domain.models.Status.Unknown -> resourcesHelper.getString(R.string.status_unknown)
    }

fun com.habernaud.rickandmorty.domain.models.Status.toColor() : Color =
    when(this){
        com.habernaud.rickandmorty.domain.models.Status.Alive -> Color.Green
        com.habernaud.rickandmorty.domain.models.Status.Dead -> Color.Red
        com.habernaud.rickandmorty.domain.models.Status.Unknown -> Color.Gray
    }