package com.habernaud.rickandmorty.commons.extensions

import org.koin.java.KoinJavaComponent.get
import com.habernaud.rickandmorty.R

private val resourcesHelper : com.habernaud.rickandmorty.commons.helpers.ResourcesHelper = get(
    com.habernaud.rickandmorty.commons.helpers.ResourcesHelper::class.java)

fun com.habernaud.rickandmorty.domain.models.Gender.toText() : String =
    when(this){
        com.habernaud.rickandmorty.domain.models.Gender.Female -> resourcesHelper.getString(R.string.gender_female)
        com.habernaud.rickandmorty.domain.models.Gender.Male -> resourcesHelper.getString(R.string.gender_male)
        com.habernaud.rickandmorty.domain.models.Gender.Genderless -> resourcesHelper.getString(R.string.gender_genderless)
        com.habernaud.rickandmorty.domain.models.Gender.Unknown -> resourcesHelper.getString(R.string.gender_unknown)
    }
