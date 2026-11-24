// the little outlined tag that marks something still settling: modules, lyrics share, which channel a build is

package dev.meowlabs.blossomtune.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.meowlabs.blossomtune.R

/** [label] in a thin pill of [color], a deep rose "BETA" unless told otherwise */
@Composable
fun BetaBadge(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.modules_beta),
    color: Color = BetaRose,
) {
    Text(
        label,
        color = color,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 1.sp,
        modifier = modifier
            .border(1.dp, color.copy(alpha = 0.8f), CircleShape)
            .padding(horizontal = 12.dp, vertical = 3.dp),
    )
}

private val BetaRose = Color(0xFF9A3050)
