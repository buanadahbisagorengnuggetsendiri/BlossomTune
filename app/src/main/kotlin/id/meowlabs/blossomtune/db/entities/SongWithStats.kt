// SongWithStats.kt

package id.meowlabs.blossomtune.db.entities

import androidx.compose.runtime.Immutable

@Immutable
data class SongWithStats(
    val id: String,
    val title: String,
    val artistName: String?,
    val thumbnailUrl: String,
    val songCountListened: Int,
    val timeListened: Long?,
    val isVideo: Boolean = false,
)
