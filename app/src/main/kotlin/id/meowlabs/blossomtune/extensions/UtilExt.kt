// UtilExt.kt

package id.meowlabs.blossomtune.extensions

fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (e: Exception) {
        null
    }
