// DonateSheet.kt

package id.meowlabs.blossomtune.ui.screens.donate

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.meowlabs.blossomtune.R
import id.meowlabs.blossomtune.core.updater.GithubProfileUrl
import id.meowlabs.blossomtune.ui.component.PopupSheetState
import id.meowlabs.blossomtune.ui.player.menu.MenuRowSurface
import id.meowlabs.blossomtune.ui.theme.InterFontFamily

/** Shows Donate as a plain PopupSheet - see docs/popup-sheet.skill.md before copying this file. */
fun PopupSheetState.showDonateSheet() {
    show(buttonBar = { DonateDoneButton(onClick = ::dismiss) }) {
        DonateSheetContent()
    }
}

@Composable
private fun DonateDoneButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(CircleShape)
            .background(MenuRowSurface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Done",
            fontFamily = InterFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ColumnScope.DonateSheetContent() {
    val context = LocalContext.current

    Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Dukung BlossomTune",
        fontFamily = InterFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = (-0.5).sp,
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
        text = "BlossomTune nggak punya kanal donasi. Kalau mau bantu, bintang di repo, laporan bug, atau kontribusi kode jauh lebih berguna.",
        fontFamily = InterFontFamily,
        fontSize = 13.5.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Bold,
    )

    Spacer(modifier = Modifier.height(24.dp))

    DonateOptionRow(
        title = "GitHub",
        subtitle = "@buanadahbisagorengnuggetsendiri",
        onClick = { context.openBrowserUrl(GithubProfileUrl) },
        iconContent = {
            Icon(
                painter = painterResource(R.drawable.github),
                contentDescription = "GitHub",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    )

    Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun DonateOptionRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    iconContent: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MenuRowSurface)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = if (subtitle != null) 12.dp else 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Small icon circle - fixed white, not themed: the brand mark inside is only
        // legible against a light badge regardless of app theme.
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            iconContent()
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = InterFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontFamily = InterFontFamily,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun Context.openBrowserUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    } catch (_: Exception) {}
}
