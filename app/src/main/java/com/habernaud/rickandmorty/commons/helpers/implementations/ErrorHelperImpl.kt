package com.habernaud.rickandmorty.commons.helpers.implementations

import com.habernaud.rickandmorty.commons.helpers.ErrorHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ErrorHelperImpl : ErrorHelper {
    private val _sharedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    override val sharedFlow = _sharedFlow.asSharedFlow()

    override fun showError(message : String) {
        _sharedFlow.tryEmit(message)
    }
}