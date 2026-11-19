// BetterLyricsProvider.kt

package dev.meowlabs.blossomtune.lyrics

import android.content.Context
import dev.meowlabs.blossomtune.betterlyrics.BetterLyrics
import dev.meowlabs.blossomtune.constants.EnableBetterLyricsKey
import dev.meowlabs.blossomtune.utils.dataStore
import dev.meowlabs.blossomtune.utils.get

object BetterLyricsProvider : LyricsProvider {
    override val name = "BetterLyrics"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableBetterLyricsKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = BetterLyrics.getLyrics(title, artist, duration, album)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
        callback: (String) -> Unit,
    ) {
        BetterLyrics.getAllLyrics(title, artist, duration, album, callback)
    }
}
