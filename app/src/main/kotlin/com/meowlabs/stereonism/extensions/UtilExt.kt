// UtilExt.kt

package com.meowlabs.stereonism.extensions

fun <T> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (e: Exception) {
        null
    }
