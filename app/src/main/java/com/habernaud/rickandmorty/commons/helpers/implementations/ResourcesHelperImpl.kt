package com.habernaud.rickandmorty.commons.helpers.implementations

import android.content.Context
import androidx.annotation.StringRes
import org.koin.java.KoinJavaComponent.get

class ResourcesHelperImpl(private val context: Context = get(Context::class.java), ) :
    com.habernaud.rickandmorty.commons.helpers.ResourcesHelper {
    override fun getString(@StringRes stringId: Int, formatArgs: Any?)
            = context.getString(stringId, formatArgs)
}