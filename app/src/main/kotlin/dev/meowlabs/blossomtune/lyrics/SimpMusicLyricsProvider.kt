// SimpMusicLyricsProvider.kt

package dev.meowlabs.blossomtune.lyrics

import android.content.Context
import dev.meowlabs.blossomtune.constants.EnableSimpMusicKey
import dev.meowlabs.blossomtune.utils.dataStore
import dev.meowlabs.blossomtune.utils.get
import com.music.simpmusic.SimpMusicLyrics

object SimpMusicLyricsProvider : LyricsProvider {
    override val name = "SimpMusic"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableSimpMusicKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = SimpMusicLyrics.getLyrics(id, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
        callback: (String) -> Unit,
    ) {
        SimpMusicLyrics.getAllLyrics(id, duration, callback)
    }
}
