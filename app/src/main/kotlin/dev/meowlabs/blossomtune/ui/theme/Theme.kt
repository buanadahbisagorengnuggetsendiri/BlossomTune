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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.staticCompositionLocalOf
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
 * Palet aplikasi. Terang = es biru + kartu putih + aksen azure (komposisi
 * mockup BlossomTune), gelap = palet lama (hitam/biru/putih).
 *
 * Nilainya bukan selera. Setiap pasangan teks/latar di bawah sudah diuji
 * kontrasnya (WCAG AA, >= 4.5) di `PaletteContrastTest`; mengubah satu warna
 * tanpa menjalankan tes itu berarti menukar keterbacaan dengan selera.
 *
 * Azure mockup #2C7BE5 gagal AA sebagai teks di halaman es (3.68) dan sebagai
 * latar teks putih (4.14). Yang dirender #2368D8 (4.62 di halaman, 5.19 dengan
 * putih); hue-nya sama, bedanya tidak kelihatan sebelah mata.
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

    // --- terang: es biru, kartu putih, aksen azure ---
    // page #E8F3FE · kartu #FFFFFF · aksen #2368D8 · tinta #0E1B2C · sekunder #4E6C8A
    val Ice = Color(0xFFE8F3FE)          // latar halaman
    val IcePanel = Color(0xFFD9E9FA)     // panel/terpilih di atas es
    val Azure = Color(0xFF2368D8)        // aksen: tombol, tautan, tab aktif
    val AzureSpec = Color(0xFF2C7BE5)    // azure mockup: gagal AA, disimpan sebagai rujukan
    val AzurePale = Color(0xFFDCEBFF)    // container aksen
    val AzureInk = Color(0xFF0B2A5B)     // teks di atas container aksen
    val SpecGray = Color(0xFF8E8E93)     // abu spec lama, disimpan sebagai rujukan
    val Navy = Color(0xFF1C2144)         // navy lama, masih dipakai fallback sampul
    val OnNavy = Color(0xFFFFFFFF)
    val NavBlue = Azure                  // tombol navbar aktif
    val Panel = IcePanel                 // panel/terpilih
    val Ink = Color(0xFF0E1B2C)          // heading, judul, teks utama
    val InkSoft = Color(0xFF3F5B78)      // sekunder: 5.70 di panel, 7.05 di putih
    val InkMuted = Color(0xFF4E6C8A)     // metadata: 4.87 di halaman, 5.47 di putih
    val Paper = Color(0xFFFFFFFF)        // kartu
    val Blossom = Color(0xFFB9A7F8)      // aksen floral, opsional - jangan jadi warna utama
    val Line = Color(0xFFCFE3F7)
    val LineSoft = Color(0xFFE3F0FE)
    val Error = Color(0xFFDC2626)        // destructive (mode gelap)
    val ErrorPale = Color(0xFFFEE2E2)
    val ErrorInk = Color(0xFF7F1D1D)
    val InverseSurface = Color(0xFF0E1B2C)
    val InverseOnSurface = Color(0xFFF4FAFF)
}

/** Warna tema default (seed artwork/player). Azure, bukan navy. */
val DefaultThemeColor = BlossomColors.Azure

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
    primary = BlossomColors.Azure,
    onPrimary = BlossomColors.OnNavy,
    primaryContainer = BlossomColors.AzurePale,
    onPrimaryContainer = BlossomColors.AzureInk,
    secondary = BlossomColors.InkMuted,
    onSecondary = BlossomColors.OnNavy,
    secondaryContainer = BlossomColors.IcePanel,
    onSecondaryContainer = BlossomColors.Ink,
    tertiary = BlossomColors.Azure,
    onTertiary = BlossomColors.OnNavy,
    tertiaryContainer = BlossomColors.AzurePale,
    onTertiaryContainer = BlossomColors.AzureInk,
    background = BlossomColors.Ice,
    onBackground = BlossomColors.Ink,
    surface = BlossomColors.Paper,
    onSurface = BlossomColors.Ink,
    surfaceVariant = BlossomColors.IcePanel,
    onSurfaceVariant = BlossomColors.InkSoft,
    surfaceTint = BlossomColors.Azure,
    inverseSurface = BlossomColors.InverseSurface,
    inverseOnSurface = BlossomColors.InverseOnSurface,
    error = Color(0xFFC81E1E),          // #DC2626 cuma 4.30 di atas es
    onError = BlossomColors.White,
    errorContainer = BlossomColors.ErrorPale,
    onErrorContainer = BlossomColors.ErrorInk,
    outline = BlossomColors.Line,
    outlineVariant = BlossomColors.LineSoft,
    scrim = BlossomColors.Black,
    // Tangga permukaan terang digambar dari halaman ke bawah, bukan ke atas:
    // kartu putih duduk DI ATAS halaman es, panel lebih gelap dari halaman.
    surfaceBright = BlossomColors.Paper,
    surfaceDim = BlossomColors.IcePanel,
    surfaceContainerLowest = BlossomColors.Paper,
    surfaceContainerLow = Color(0xFFF4FAFF),
    surfaceContainer = BlossomColors.IcePanel,
    surfaceContainerHigh = BlossomColors.Line,
    surfaceContainerHighest = Color(0xFFC3DAF1)
)

/**
 * blossomtune renders light (najstore.id palette).
 *
 * Diganti dari `true` waktu palet terang dipasang; sebelumnya `true` karena palet
 * terang belum pernah kelar — UI-nya hardcode teks putih dan kaca gelap, jadi di HP
 * mode terang appnya kelihatan rusak, bukan terang. Sekarang sisanya sudah ikut
 * skema (`MaterialTheme.colorScheme`), jadi mode terang bisa dipakai.
 *
 * Satu switch, bukan keputusan terpisah di tiap call site.
 */
const val ForceDarkTheme = false

/**
 * Permukaan sheet dan bilah kaca — yang dulu hardcode gelap.
 *
 * "Kaca gelap" di atas halaman putih jadi kotak hitam, dan tinta putih di atasnya
 * hilang. Jadi permukaannya ikut mode: di terang `sheet` putih, `row` abu najstore
 * (`#F4F7FB`), bukan `#080A0F`/`#10151F`. Nilai gelap tidak diubah sama sekali.
 */
data class BlossomSurfaces(
    val chrome: Color,   // bilah kaca atas/bawah (bottom bar, mini player)
    val sheet: Color,    // latar sheet
    val row: Color,      // baris/kartu di dalam sheet
)

val DarkSurfaces = BlossomSurfaces(
    chrome = Color(0xFF0C0F15),
    sheet = Color(0xFF080A0F),
    row = Color(0xFF10151F),
)

val LightSurfaces = BlossomSurfaces(
    chrome = Color(0xFFFFFFFF),   // kaca putih di atas halaman es
    sheet = Color(0xFFFFFFFF),    // kartu putih
    row = Color(0xFFF4FAFF),      // baris = es muda
)

/** Default mengikuti `ForceDarkTheme`, jadi kode yang belum ikut mode tetap benar. */
val LocalBlossomSurfaces = staticCompositionLocalOf { if (ForceDarkTheme) DarkSurfaces else LightSurfaces }

/** Permukaan untuk mode yang sedang aktif. Harus dibaca di scope `@Composable`. */
val BlossomSurface: BlossomSurfaces
    @Composable @ReadOnlyComposable get() = LocalBlossomSurfaces.current

/**
 * Tinta di atas [BlossomSurfaces.sheet] dan `.row`.
 *
 * Dulu semua layar sheet menulis `Color.White` langsung, karena sheet-nya kaca gelap
 * hardcode. Begitu sheet ikut mode, tinta putih di atas sheet putih hilang - jadi
 * tintanya dipusatkan di sini, satu tempat, bukan `Color.White` yang tersebar.
 */
object BlossomInk {
    val Primary = Color(0xFF0E1B2C)
    val Muted = Color(0xFF4E6C8A)
    val Divider = Color(0xFF0E1B2C).copy(alpha = 0.08f)
    /** Bulatan ikon: tinta pekat dengan ikon putih. */
    val Chip = Color(0xFF0E1B2C)
    val OnChip = Color(0xFFFFFFFF)
}

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
        ) {
            CompositionLocalProvider(
                LocalBlossomSurfaces provides if (darkTheme) DarkSurfaces else LightSurfaces,
                content = content
            )
        }
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
        listOf(BlossomColors.Navy, BlossomColors.Black)
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
