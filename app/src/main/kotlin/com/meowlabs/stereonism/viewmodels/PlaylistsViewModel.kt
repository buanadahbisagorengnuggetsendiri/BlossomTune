// PlaylistsViewModel.kt

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.meowlabs.stereonism.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meowlabs.stereonism.constants.AddToPlaylistSortDescendingKey
import com.meowlabs.stereonism.constants.AddToPlaylistSortTypeKey
import com.meowlabs.stereonism.constants.PlaylistSortType
import com.meowlabs.stereonism.db.MusicDatabase
import com.meowlabs.stereonism.extensions.toEnum
import com.meowlabs.stereonism.utils.SyncUtils
import com.meowlabs.stereonism.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel
@Inject
constructor(
    @ApplicationContext context: Context,
    database: MusicDatabase,
    private val syncUtils: SyncUtils,
) : ViewModel() {
    val allPlaylists =
        context.dataStore.data
            .map {
                it[AddToPlaylistSortTypeKey].toEnum(PlaylistSortType.CREATE_DATE) to (it[AddToPlaylistSortDescendingKey]
                    ?: true)
            }.distinctUntilChanged()
            .flatMapLatest { (sortType, descending) ->
                database.playlists(sortType, descending)
            }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    suspend fun sync() {
        syncUtils.syncSavedPlaylists()
    }
}
