// UriUtils.kt

package dev.meowlabs.blossomtune.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.UriHandler
import dev.meowlabs.blossomtune.R

fun UriHandler.safeOpenUri(context: Context, uri: String) {
    if (uri.isBlank()) return

    runCatching {
        openUri(uri)
    }.onFailure {
        Toast.makeText(
            context,
            context.getString(R.string.pc_error_no_app),
            Toast.LENGTH_SHORT
        ).show()
    }
}
