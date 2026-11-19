// QQMusicLyricsProvider.kt

package dev.meowlabs.blossomtune.lyrics

import android.content.Context
import com.music.directlyrics.QQMusic
import dev.meowlabs.blossomtune.constants.EnableQQMusicKey
import dev.meowlabs.blossomtune.utils.dataStore
import dev.meowlabs.blossomtune.utils.get

object QQMusicLyricsProvider : LyricsProvider {
    override val name = "QQMusic"
    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableQQMusicKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = QQMusic.getLyrics(title, artist, duration)
}
