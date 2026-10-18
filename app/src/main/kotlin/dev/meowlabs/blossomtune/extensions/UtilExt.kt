// UtilExt.kt

package dev.meowlabs.blossomtune.extensions

fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (e: Exception) {
        null
    }
