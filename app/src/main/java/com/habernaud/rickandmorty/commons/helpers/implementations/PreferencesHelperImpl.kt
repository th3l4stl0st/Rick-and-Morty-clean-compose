package com.habernaud.rickandmorty.commons.helpers.implementations

import android.content.Context
import android.content.SharedPreferences
import org.koin.java.KoinJavaComponent.get

class PreferencesHelperImpl(context : Context = get(Context::class.java)) : BasePreferencesHelper(),
    com.habernaud.rickandmorty.commons.helpers.PreferencesHelper {

    override val sharedPreferences: SharedPreferences
            = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES = "preferences"
        private const val LOCATIONS_SYNCHRONIZATION_SYNC_DONE = "locations_synchronization_sync_done"
        private const val EPISODES_SYNCHRONIZATION_SYNC_DONE = "episodes_synchronization_sync_done"
        private const val CHARACTERLIST_SYNCHRONIZATION_SYNC_DONE = "characterList_synchronization_sync_done"
    }

    override var locationsSynchronizationDone : Boolean
        get() = getValue(LOCATIONS_SYNCHRONIZATION_SYNC_DONE, false)
        set(value) = setValue(LOCATIONS_SYNCHRONIZATION_SYNC_DONE, value)

    override var episodesSynchronizationDone : Boolean
        get() = getValue(EPISODES_SYNCHRONIZATION_SYNC_DONE, false)
        set(value) = setValue(EPISODES_SYNCHRONIZATION_SYNC_DONE, value)


    override var characterListSynchronizationDone : Boolean
        get() = getValue(CHARACTERLIST_SYNCHRONIZATION_SYNC_DONE, false)
        set(value) = setValue(CHARACTERLIST_SYNCHRONIZATION_SYNC_DONE, value)
}