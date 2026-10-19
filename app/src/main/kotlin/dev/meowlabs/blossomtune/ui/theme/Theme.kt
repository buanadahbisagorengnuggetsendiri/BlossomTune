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
 * Palet aplikasi. Terang = navy/abu/putih (spec dari tangkapan layar aplikasi
 * musik), gelap = palet lama (hitam/biru/putih).
 *
 * Nilainya bukan selera. Setiap pasangan teks/latar di bawah sudah diuji
 * kontrasnya (WCAG AA, >= 4.5) di `PaletteContrastTest`; mengubah satu warna
 * tanpa menjalankan tes itu berarti menukar keterbacaan dengan selera.
 *
 * Abu spec #8E8E93 gagal AA di atas putih (3.26) — sama kasusnya dengan
 * #3478F6/#66758F zaman najstore. Yang dirender #73737B (4.70), hue-nya
 * sama, bedanya tidak kelihatan sebelah mata. Nilai spec disimpan di
 * `BlossomColors.SpecGray` supaya rujukan desainnya tidak hilang.
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

    // --- terang: navy/abu/putih, monokrom ---
    // navy #1C2144 · panel #F2F2F7 · putih #FFFFFF · abu #73737B
    val SpecGray = Color(0xFF8E8E93)     // abu spec: 3.26 di atas putih -> gagal AA
    val Navy = Color(0xFF1C2144)         // aksen + tinta utama: 15.55 di atas putih
    val OnNavy = Color(0xFFFFFFFF)        // teks di atas tombol navy
    val Panel = Color(0xFFF2F2F7)        // panel/terpilih: abu netral
    val Ink = Color(0xFF1C2144)          // heading, judul, teks utama (= Navy)
    val InkSoft = Color(0xFF6D6D77)      // abu secondary: 5.12 di atas putih, 4.59 di atas panel
    val Paper = Color(0xFFFFFFFF)        // latar halaman
    val Blossom = Color(0xFFB9A7F8)       // aksen floral, opsional - jangan jadi warna utama
    // Outline tidak ada di palet; diturunkan dari abu netral.
    val Line = Color(0xFFE5E5EA)
    val LineSoft = Color(0xFFF2F2F7)
    val Error = Color(0xFFDC2626)         // destructive
    val ErrorPale = Color(0xFFFEE2E2)
    val ErrorInk = Color(0xFF7F1D1D)
    val InverseSurface = Color(0xFF1C2144)
    val InverseOnSurface = Color(0xFFF9FAFB)
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
    primary = BlossomColors.Navy,
    onPrimary = BlossomColors.OnNavy,
    primaryContainer = BlossomColors.Panel,
    onPrimaryContainer = BlossomColors.Navy,
    secondary = BlossomColors.Navy,
    onSecondary = BlossomColors.OnNavy,
    secondaryContainer = BlossomColors.Panel,
    onSecondaryContainer = BlossomColors.Navy,
    tertiary = BlossomColors.Navy,
    onTertiary = BlossomColors.OnNavy,
    tertiaryContainer = BlossomColors.Panel,
    onTertiaryContainer = BlossomColors.Navy,
    background = BlossomColors.Paper,
    onBackground = BlossomColors.Ink,
    surface = BlossomColors.White,
    onSurface = BlossomColors.Ink,
    surfaceVariant = BlossomColors.Panel,
    onSurfaceVariant = BlossomColors.InkSoft,
    surfaceTint = BlossomColors.Navy,
    inverseSurface = BlossomColors.InverseSurface,
    inverseOnSurface = BlossomColors.InverseOnSurface,
    error = BlossomColors.Error,
    onError = BlossomColors.White,
    errorContainer = BlossomColors.ErrorPale,
    onErrorContainer = BlossomColors.ErrorInk,
    outline = BlossomColors.Line,
    outlineVariant = BlossomColors.LineSoft,
    scrim = BlossomColors.Black,
    // Tangga permukaan terang digambar dari halaman ke bawah, bukan ke atas:
    // kartu harus LEBIH GELAP dari latar, kalau tidak kartunya hilang.
    // (Di mode gelap kebalikannya — surface lebih terang dari background.)
    surfaceBright = BlossomColors.White,
    surfaceDim = BlossomColors.LineSoft,
    surfaceContainerLowest = BlossomColors.White,
    surfaceContainerLow = Color(0xFFF7F7FA),
    surfaceContainer = BlossomColors.Panel,
    surfaceContainerHigh = Color(0xFFEBEBF0),
    surfaceContainerHighest = BlossomColors.Line
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
    chrome = Color(0xFFFFFFFF),   // kaca putih di atas halaman putih
    sheet = Color(0xFFFFFFFF),    // kartu putih
    row = Color(0xFFF2F2F7),      // baris = abu panel
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
    val Primary = Color(0xFF1C2144)
    val Muted = Color(0xFF6D6D77)
    val Divider = Color(0xFF1C2144).copy(alpha = 0.08f)
    /** Bulatan ikon: tinta pekat dengan ikon putih. */
    val Chip = Color(0xFF1C2144)
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
