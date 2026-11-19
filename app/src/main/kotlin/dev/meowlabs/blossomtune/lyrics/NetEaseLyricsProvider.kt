// NetEaseLyricsProvider.kt

package dev.meowlabs.blossomtune.lyrics

import android.content.Context
import com.music.directlyrics.NetEase
import dev.meowlabs.blossomtune.constants.EnableNetEaseKey
import dev.meowlabs.blossomtune.utils.dataStore
import dev.meowlabs.blossomtune.utils.get

object NetEaseLyricsProvider : LyricsProvider {
    override val name = "NetEase"
    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableNetEaseKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = NetEase.getLyrics(title, artist, duration)
}
