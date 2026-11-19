// LinkPlayer.kt

package dev.meowlabs.blossomtune.link

import dev.meowlabs.blossomtune.playback.PlayerConnection

// the open app's player, for a paired device that plays, skips or changes something here; null while no screen is up
object LinkPlayer {
    @Volatile var connection: PlayerConnection? = null

    // a quiet stand-in from android auto, for a phone in the car whose own blossomtune screen was never opened
    @Volatile var background: PlayerConnection? = null

    // the screen's player when there is one, otherwise the car's
    fun current(): PlayerConnection? = connection ?: background

    // only the screen that set it may clear it, so a recreated activity doesn't wipe the new one's player
    fun release(connection: PlayerConnection?) {
        if (connection != null && this.connection === connection) this.connection = null
    }

    fun releaseBackground(connection: PlayerConnection?) {
        if (connection != null && background === connection) background = null
    }
}
