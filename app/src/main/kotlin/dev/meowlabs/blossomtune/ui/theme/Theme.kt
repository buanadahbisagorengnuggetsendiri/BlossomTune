// Theme.kt

package dev.meowlabs.blossomtune.ui.theme

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.score.Score

/**
 * Palet aplikasi: **hitam** latar, **biru** aksen, **putih** teks.
 *
 * Nilainya bukan selera. Setiap pasangan teks/latar di bawah sudah diuji
 * kontrasnya (WCAG AA, >= 4.5) di `PaletteContrastTest`; mengubah satu warna
 * tanpa menjalankan tes itu berarti menukar keterbacaan dengan selera.
 *
 * Biru ada dua nada, bukan satu: biru yang enak dibaca di atas hitam terlalu
 * terang untuk latar tombol berisi teks gelap, dan biru yang enak dijadikan
 * latar tombol terlalu gelap untuk dibaca di atas hitam.
 */
object BlossomColors {
    // --- gelap ---
    val Blue = Color(0xFF4D8DFF)          // aksen utama di latar hitam
    val OnBlue = Color(0xFF04101F)        // teks di atas tombol biru
    val BlueContainer = Color(0xFF12224A)
    val OnBlueContainer = Color(0xFFD9E6FF)
    val Steel = Color(0xFFA8C4EC)
    val SteelContainer = Color(0xFF16233A)
    val OnSteelContainer = Color(0xFFE4ECF9)
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
    val NightCard = Color(0xFF101827)     // kartu di atas latar hitam
    val OnNightCard = Color(0xFFC9D6E8)

    // --- terang ---
    val BlueDeep = Color(0xFF1A56DB)
    val BluePale = Color(0xFFDCE7FF)
    val OnBluePale = Color(0xFF06122E)
    val Indigo = Color(0xFF1E3A8A)
    val PaperSoft = Color(0xFFEEF2F8)
    val Ink = Color(0xFF0B1220)           // teks di atas latar putih
    val InkSoft = Color(0xFF1B2430)
    val PaperIndigo = Color(0xFFE6EDF9)
}

val DefaultThemeColor = BlossomColors.Blue

val DarkPalette: ColorScheme = darkColorScheme(
    primary = BlossomColors.Blue,
    onPrimary = BlossomColors.OnBlue,
    primaryContainer = BlossomColors.BlueContainer,
    onPrimaryContainer = BlossomColors.OnBlueContainer,
    secondary = BlossomColors.Steel,
    onSecondary = BlossomColors.OnBlue,
    secondaryContainer = BlossomColors.SteelContainer,
    onSecondaryContainer = BlossomColors.OnSteelContainer,
    background = BlossomColors.Black,
    onBackground = BlossomColors.White,
    surface = BlossomColors.Black,
    onSurface = BlossomColors.White,
    surfaceVariant = BlossomColors.NightCard,
    onSurfaceVariant = BlossomColors.OnNightCard,
    surfaceTint = BlossomColors.Black
)

val LightPalette: ColorScheme = lightColorScheme(
    primary = BlossomColors.BlueDeep,
    onPrimary = BlossomColors.White,
    primaryContainer = BlossomColors.BluePale,
    onPrimaryContainer = BlossomColors.OnBluePale,
    secondary = BlossomColors.Indigo,
    onSecondary = BlossomColors.White,
    secondaryContainer = BlossomColors.PaperIndigo,
    onSecondaryContainer = BlossomColors.Ink,
    background = BlossomColors.White,
    onBackground = BlossomColors.Ink,
    surface = BlossomColors.White,
    onSurface = BlossomColors.Ink,
    surfaceVariant = BlossomColors.PaperSoft,
    onSurfaceVariant = BlossomColors.InkSoft,
    surfaceTint = BlossomColors.White
)

/**
 * blossomtune renders dark-only.
 *
 * The light palette was never finished - large parts of the UI hardcode white text and dark
 * glass - so on a light-mode phone the app looked broken rather than light. Pinned here as one
 * switch instead of being decided independently at every call site.
 */
const val ForceDarkTheme = true

@Composable
fun BlossomTuneTheme(
    darkTheme: Boolean = ForceDarkTheme,
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    // Dynamic (Material You) colour is deliberately not used - the app ships its own palette.
    // The branch is gone rather than dead-but-present: dynamic*ColorScheme is API 31.
    // Skema diambil dari val top-level (`DarkPalette`/`LightPalette`) supaya
    // `PaletteContrastTest` bisa menguji warna yang benar-benar dipakai aplikasi,
    // bukan salinannya.
    val baseColorScheme = if (darkTheme) DarkPalette else LightPalette

    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme) {
        if (darkTheme && pureBlack) {
            baseColorScheme.pureBlack(true)
        } else {
            baseColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
    ) {
        ProvideTextStyle(
            value = AppTypography.bodyLarge,
            content = content
        )
    }
}

fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    return Color(rankedColors.first())
}

fun Bitmap.extractGradientColors(): List<Color> {
    val extractedColors = Palette.from(this)
        .maximumColorCount(64)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    val orderedColors = Score.score(extractedColors, 2, 0xff4285f4.toInt(), true)
        .sortedByDescending { Color(it).luminance() }

    return if (orderedColors.size >= 2)
        listOf(Color(orderedColors[0]), Color(orderedColors[1]))
    else
        listOf(BlossomColors.BlueDeep, BlossomColors.Black)
}

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
