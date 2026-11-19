// DeezerLyricsProvider.kt

package dev.meowlabs.blossomtune.lyrics

import android.content.Context
import com.music.directlyrics.Deezer
import dev.meowlabs.blossomtune.constants.EnableDeezerKey
import dev.meowlabs.blossomtune.utils.dataStore
import dev.meowlabs.blossomtune.utils.get

object DeezerLyricsProvider : LyricsProvider {
    override val name = "Deezer"
    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableDeezerKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = Deezer.getLyrics(title, artist, duration)
}
