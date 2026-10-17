// LyricsHelperEntryPoint.kt

package com.meowlabs.stereonism.di

import com.meowlabs.stereonism.lyrics.LyricsHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LyricsHelperEntryPoint {
    fun lyricsHelper(): LyricsHelper
}
