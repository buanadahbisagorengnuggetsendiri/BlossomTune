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
import androidx.compose.runtime.staticCompositionLocalOf
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
 * Palet aplikasi. Terang = petal + kartu putih + aksen deep rose (palet
 * Cherry Blossom), gelap = malam plum + blossom pink + petal.
 *
 * Nilainya bukan selera. Setiap pasangan teks/latar di bawah sudah diuji
 * kontrasnya (WCAG AA, >= 4.5) di `PaletteContrastTest`; mengubah satu warna
 * tanpa menjalankan tes itu berarti menukar keterbacaan dengan selera.
 *
 * Rose palet #C84870 gagal AA sebagai teks kecil di halaman petal (3.91),
 * jadi yang dirender #9A3050 (6.20 di halaman, 7.21 dengan putih); hue-nya
 * sama, bedanya tidak kelihatan sebelah mata. Nilai palet tetap disimpan
 * di [BlossomColors.RoseSpec] sebagai rujukan desain.
 */
object BlossomColors {
    // --- gelap: malam plum + blossom pink + petal ---
    val Blossom = Color(0xFFE890A8)       // aksen utama di latar malam (Cherry Blossom)
    val OnBlossom = Color(0xFF2A121B)     // teks di atas tombol blossom
    val BlossomContainer = Color(0xFF3A1220)
    val OnBlossomContainer = Color(0xFFF5C0CC)  // Blush
    val Blush = Color(0xFFF5C0CC)          // sekunder terang di latar malam
    val BlushContainer = Color(0xFF2A1018)
    val OnBlushContainer = Color(0xFFF3D3DB)
    val Night = Color(0xFF1A0A11)          // latar malam, bukan hitam murni
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
    val NightCard = Color(0xFF241019)      // kartu di atas latar malam
    val OnNightCard = Color(0xFFEBCED6)

    // --- terang: petal, kartu putih, aksen deep rose ---
    // page #FAEAEE, kartu #FFFFFF, aksen #9A3050, tinta #2A121B, sekunder #7C4A5C
    val Petal = Color(0xFFFAEAEE)          // latar halaman
    val PetalPanel = Color(0xFFF6DCE1)     // panel/terpilih di atas petal
    val DeepRose = Color(0xFF9A3050)       // aksen: tombol, tautan, tab aktif
    val RoseSpec = Color(0xFFC84870)       // rose palet: gagal AA, disimpan sebagai rujukan
    val RosePale = Color(0xFFFADADD)       // container aksen
    val RoseInk = Color(0xFF4A1424)        // teks di atas container aksen
    val SpecGray = Color(0xFF8E8E93)       // abu spec lama, disimpan sebagai rujukan
    val Plum = Color(0xFF2A121B)           // plum tua, masih dipakai fallback sampul
    val Ink = Color(0xFF2A121B)            // heading, judul, teks utama
    val InkSoft = Color(0xFF5E3342)        // sekunder: 8.01 di panel, 10.35 di putih
    val InkMuted = Color(0xFF7C4A5C)       // metadata: 6.04 di halaman, 7.02 di putih
    val Paper = Color(0xFFFFFFFF)          // kartu
    val Line = Color(0xFFE8C6CF)
    val Hairline = Color(0xFF8A8F98)       // garis rambut: terbaca di hitam dan di putih
    val LineSoft = Color(0xFFF3DEE3)
    val Error = Color(0xFFDC2626)          // destructive di atas permukaan terang
    val ErrorOnDark = Color(0xFFFFB4AB)    // #DC2626 cuma 4.35 di atas hitam
    val OnErrorOnDark = Color(0xFF690005)
    val ErrorPale = Color(0xFFFEE2E2)
    val ErrorInk = Color(0xFF7F1D1D)
    val InverseSurface = Color(0xFF2A121B)
    val InverseOnSurface = Color(0xFFFDF3F5)
}

/** Warna tema default (seed artwork/player). Deep rose, bukan abu netral. */
val DefaultThemeColor = BlossomColors.DeepRose

val DarkPalette: ColorScheme = darkColorScheme(
    primary = BlossomColors.Blossom,
    onPrimary = BlossomColors.OnBlossom,
    primaryContainer = BlossomColors.BlossomContainer,
    onPrimaryContainer = BlossomColors.OnBlossomContainer,
    secondary = BlossomColors.Blush,
    onSecondary = BlossomColors.OnBlossom,
    secondaryContainer = BlossomColors.BlushContainer,
    onSecondaryContainer = BlossomColors.OnBlushContainer,
    background = BlossomColors.Night,
    onBackground = BlossomColors.Petal,
    surface = BlossomColors.Night,
    onSurface = BlossomColors.Petal,
    surfaceVariant = BlossomColors.NightCard,
    onSurfaceVariant = BlossomColors.OnNightCard,
    surfaceTint = BlossomColors.Night,
    error = BlossomColors.ErrorOnDark,
    onError = BlossomColors.OnErrorOnDark
)

val LightPalette: ColorScheme = lightColorScheme(
    primary = BlossomColors.DeepRose,
    onPrimary = BlossomColors.White,
    primaryContainer = BlossomColors.RosePale,
    onPrimaryContainer = BlossomColors.RoseInk,
    secondary = BlossomColors.InkMuted,
    onSecondary = BlossomColors.White,
    secondaryContainer = BlossomColors.PetalPanel,
    onSecondaryContainer = BlossomColors.Ink,
    tertiary = BlossomColors.DeepRose,
    onTertiary = BlossomColors.White,
    tertiaryContainer = BlossomColors.RosePale,
    onTertiaryContainer = BlossomColors.RoseInk,
    background = BlossomColors.Petal,
    onBackground = BlossomColors.Ink,
    surface = BlossomColors.Paper,
    onSurface = BlossomColors.Ink,
    surfaceVariant = BlossomColors.PetalPanel,
    onSurfaceVariant = BlossomColors.InkSoft,
    surfaceTint = BlossomColors.DeepRose,
    inverseSurface = BlossomColors.InverseSurface,
    inverseOnSurface = BlossomColors.InverseOnSurface,
    error = Color(0xFFC81E1E),          // #DC2626 cuma 4.30 di atas es, 4.94 di atas petal
    onError = BlossomColors.White,
    errorContainer = BlossomColors.ErrorPale,
    onErrorContainer = BlossomColors.ErrorInk,
    outline = BlossomColors.Line,
    outlineVariant = BlossomColors.LineSoft,
    scrim = BlossomColors.Black,
    // Tangga permukaan terang digambar dari halaman ke bawah, bukan ke atas:
    // kartu putih duduk DI ATAS halaman petal, panel lebih gelap dari halaman.
    surfaceBright = BlossomColors.Paper,
    surfaceDim = BlossomColors.PetalPanel,
    surfaceContainerLowest = BlossomColors.Paper,
    surfaceContainerLow = Color(0xFFFDF3F5),
    surfaceContainer = BlossomColors.PetalPanel,
    surfaceContainerHigh = BlossomColors.Line,
    surfaceContainerHighest = Color(0xFFE2B7C2)
)

/**
 * Permukaan sheet dan bilah kaca — yang di basis Mochy masih hardcode gelap
 * (`MenuSurface` `#0B0B0C`, `MenuRowSurface` `#161619`, `GlassChromeColor`
 * `#131313`).
 *
 * "Kaca gelap" di atas halaman petal jadi kotak hitam, dan tinta putih di atasnya
 * hilang. Jadi permukaannya ikut mode; nilai gelapnya malam plum, bukan hitam
 * basis, supaya mode gelap ikut palet Cherry Blossom juga.
 */
data class BlossomSurfaces(
    val chrome: Color,   // bilah kaca: bottom bar, mini player, toolbar
    val sheet: Color,    // latar sheet
    val row: Color,      // baris/kartu di dalam sheet
    val page: Color,     // latar layar yang melukis sendiri
)

val DarkSurfaces = BlossomSurfaces(
    chrome = Color(0xFF1E0E14),
    sheet = Color(0xFF160A0F),
    row = Color(0xFF221017),
    page = BlossomColors.Night,
)

val LightSurfaces = BlossomSurfaces(
    chrome = Color(0xFFFFFFFF),   // kaca putih di atas halaman petal
    sheet = Color(0xFFFFFFFF),    // kartu putih
    row = Color(0xFFF7E6EA),      // baris = petal muda
    page = BlossomColors.Petal,
)

val LocalBlossomSurfaces = staticCompositionLocalOf { DarkSurfaces }

/** Permukaan untuk mode yang sedang aktif. Harus dibaca di scope `@Composable`. */
val BlossomSurface: BlossomSurfaces
    @Composable @ReadOnlyComposable get() = LocalBlossomSurfaces.current

/**
 * Tinta di atas [BlossomSurface.sheet] dan `.row`.
 *
 * Di basis Mochy semua layar sheet menulis `Color.White` langsung karena sheet-nya
 * kaca gelap hardcode. Begitu sheet ikut mode, tinta putih itu hilang di sheet
 * putih — jadi tintanya dipusatkan di sini, satu tempat, bukan tersebar.
 */
data class BlossomInkSet(
    val primary: Color,
    val muted: Color,
    val divider: Color,
    val chip: Color,
    val onChip: Color,
)

val DarkInk = BlossomInkSet(
    primary = BlossomColors.Petal,
    muted = Color(0xFFD3A9B6),
    divider = BlossomColors.White.copy(alpha = 0.08f),
    chip = BlossomColors.White,
    onChip = BlossomColors.Black,
)

val LightInk = BlossomInkSet(
    primary = BlossomColors.Ink,
    muted = BlossomColors.InkMuted,
    divider = BlossomColors.Ink.copy(alpha = 0.08f),
    chip = BlossomColors.Ink,
    onChip = BlossomColors.White,
)

val LocalBlossomInk = staticCompositionLocalOf { DarkInk }

/** Mode gelap aktif? Dipakai kit warna per-fitur yang tidak memakai colorScheme. */
val LocalBlossomDark = staticCompositionLocalOf { true }

/**
 * Tinta pemutar. Dulu pemutar selalu di atas artwork, jadi seluruh ikon dan
 * teksnya putih mati. Sekarang pemutar ikut mode: di mode terang permukaannya petal,
 * jadi tintanya tinta halaman. Nama sendiri supaya niatnya kebaca di ui/player.
 */
val LocalPlayerInk = staticCompositionLocalOf { BlossomColors.White }
val PlayerInk: Color @Composable @ReadOnlyComposable get() = LocalPlayerInk.current

object BlossomInk {
    val Primary: Color @Composable @ReadOnlyComposable get() = LocalBlossomInk.current.primary
    val Muted: Color @Composable @ReadOnlyComposable get() = LocalBlossomInk.current.muted
    val Divider: Color @Composable @ReadOnlyComposable get() = LocalBlossomInk.current.divider
    val Chip: Color @Composable @ReadOnlyComposable get() = LocalBlossomInk.current.chip
    val OnChip: Color @Composable @ReadOnlyComposable get() = LocalBlossomInk.current.onChip
}

/**
 * Ikut mode sistem. Dulu dipaku `true` karena sheet basis Mochy hardcode kaca
 * gelap; sekarang permukaan (`BlossomSurfaces`) dan tinta sheet (`BlossomInk`)
 * ikut mode, jadi palet terang benar-benar dirender.
 *
 * Yang sengaja tetap gelap: video, TV, dan badge di atas artwork. Pemutar ikut
 * petal kecuali bagian yang memang duduk di atas video/artwork.
 */
const val ForceDarkTheme = false

@Composable
fun BlossomTuneTheme(
    darkTheme: Boolean = ForceDarkTheme,
    pureBlack: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    // Dynamic (Material You) colour is deliberately not used - the app ships its own palette.
    // The branch is gone rather than dead-but-present: dynamic*ColorScheme is API 31.
    // Skema diambil dari val top-level supaya `PaletteContrastTest` menguji warna
    // yang benar-benar dirender, bukan salinannya.
    val baseColorScheme = if (darkTheme) DarkPalette else LightPalette

    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme) {
        if (darkTheme && pureBlack) {
            baseColorScheme.pureBlack(true)
        } else {
            baseColorScheme
        }
    }

    val font = InterFontFamily
    val typography = remember(font) { appTypography(font) }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
    ) {
        ProvideTextStyle(
            value = typography.bodyLarge,
        ) {
            CompositionLocalProvider(
                LocalBlossomSurfaces provides if (darkTheme) DarkSurfaces else LightSurfaces,
                LocalBlossomInk provides if (darkTheme) DarkInk else LightInk,
                LocalBlossomDark provides darkTheme,
                LocalPlayerInk provides if (darkTheme) BlossomColors.White else BlossomColors.Ink,
                content = content,
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
        listOf(Color(0xFF595959), Color(0xFF0D0D0D))
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
