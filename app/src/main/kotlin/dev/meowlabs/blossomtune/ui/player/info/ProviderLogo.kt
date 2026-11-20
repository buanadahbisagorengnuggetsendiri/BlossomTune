// ProviderLogo.kt

package dev.meowlabs.blossomtune.ui.player.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DownloadDone
import androidx.compose.material.icons.rounded.Smartphone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import dev.meowlabs.blossomtune.R
import dev.meowlabs.blossomtune.importer.account.AccountService
import dev.meowlabs.blossomtune.modules.SourceModules
import dev.meowlabs.blossomtune.playback.PlayingFrom
import dev.meowlabs.blossomtune.ui.screens.setup.onboarding.ProviderSquircle
import dev.meowlabs.blossomtune.ui.screens.setup.onboarding.SquircleShape
import dev.meowlabs.blossomtune.ui.theme.BlossomInk

/** a provider's own icon, the same squircle the onboarding uses; a letter for a module with none */
@Composable
internal fun ProviderLogo(providerId: String, name: String, size: Dp) {
    when (providerId) {
        SourceModules.YouTubeId -> ProviderSquircle(service = AccountService.YOUTUBE_MUSIC, size = size)
        "applemusic" -> ProviderSquircle(service = AccountService.APPLE_MUSIC, size = size)
        "monochrome" -> Image(
            painter = painterResource(R.drawable.provider_monochrome),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size).clip(SquircleShape),
        )
        PlayingFrom.Device, PlayingFrom.Downloads -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size).clip(SquircleShape).background(Color.White.copy(alpha = 0.9f)),
        ) {
            Icon(
                imageVector = if (providerId == PlayingFrom.Device) Icons.Rounded.Smartphone else Icons.Rounded.DownloadDone,
                contentDescription = name,
                tint = Color.Black.copy(alpha = 0.75f),
                modifier = Modifier.size(size * 0.5f),
            )
        }
        else -> Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size).clip(SquircleShape).background(Color.White.copy(alpha = 0.16f)),
        ) {
            Text(text = name.take(1).uppercase(), color = BlossomInk.Primary, fontSize = (size.value * 0.42f).sp, fontWeight = FontWeight.Bold)
        }
    }
}
