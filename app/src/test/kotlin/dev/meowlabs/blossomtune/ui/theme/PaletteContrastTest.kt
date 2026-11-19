package dev.meowlabs.blossomtune.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Palet es/azure (terang) dan hitam/biru/putih (gelap) dikunci di sini.
 *
 * Warna aksen gampang diganti diam-diam lewat satu commit kecil, dan hasilnya baru
 * kelihatan sebagai teks yang tidak terbaca di HP orang. Tes ini menahan itu: setiap
 * pasangan teks/latar harus lolos WCAG AA (>= 4.5) sebelum boleh masuk rilis.
 *
 * Dua palet diuji, bukan satu: `ForceDarkTheme` sudah `false`, jadi keduanya benar-benar
 * dirender tergantung mode sistem, dan keduanya harus lolos AA.
 */
class PaletteContrastTest {

    private fun contrast(fg: Color, bg: Color): Double {
        val a = fg.luminance().toDouble()
        val b = bg.luminance().toDouble()
        val hi = maxOf(a, b)
        val lo = minOf(a, b)
        return (hi + 0.05) / (lo + 0.05)
    }

    private fun assertReadable(fg: Color, bg: Color, where: String) {
        val r = contrast(fg, bg)
        assertTrue("$where: kontras %.2f < 4.5".format(r), r >= 4.5)
    }

    @Test
    fun darkPalette_isReadableOnEverySurface() {
        assertReadable(DarkPalette.onBackground, DarkPalette.background, "teks di latar")
        assertReadable(DarkPalette.onSurface, DarkPalette.surface, "teks di surface")
        assertReadable(DarkPalette.onSurfaceVariant, DarkPalette.surfaceVariant, "teks di kartu")
        assertReadable(DarkPalette.primary, DarkPalette.background, "aksen biru di latar hitam")
        assertReadable(DarkPalette.onPrimary, DarkPalette.primary, "teks di tombol biru")
        assertReadable(DarkPalette.onPrimaryContainer, DarkPalette.primaryContainer, "teks di container")
        assertReadable(DarkPalette.onSecondaryContainer, DarkPalette.secondaryContainer, "teks di container kedua")
        assertReadable(DarkPalette.error, DarkPalette.background, "error di latar hitam")
    }

    @Test
    fun lightPalette_isReadableOnEverySurface() {
        assertReadable(LightPalette.onBackground, LightPalette.background, "teks di latar")
        assertReadable(LightPalette.onSurface, LightPalette.surface, "teks di surface")
        assertReadable(LightPalette.onSurfaceVariant, LightPalette.surfaceVariant, "teks di kartu")
        assertReadable(LightPalette.primary, LightPalette.background, "aksen azure di halaman es")
        assertReadable(LightPalette.onPrimary, LightPalette.primary, "teks di tombol azure")
        assertReadable(LightPalette.onPrimaryContainer, LightPalette.primaryContainer, "teks di container")
        assertReadable(LightPalette.onSecondaryContainer, LightPalette.secondaryContainer, "teks di container kedua")
    }

    /** `pureBlack` hanya boleh menggelapkan, tidak boleh membuat teksnya hilang. */
    @Test
    fun pureBlack_keepsTextReadable() {
        val black = DarkPalette.pureBlack(true)
        assertEquals(BlossomColors.Black, black.background)
        assertEquals(BlossomColors.Black, black.surface)
        assertReadable(black.onBackground, black.background, "teks di latar pure-black")
        assertReadable(black.onSurface, black.surface, "teks di surface pure-black")

        val untouched = DarkPalette.pureBlack(false)
        assertEquals(DarkPalette.background, untouched.background)
        assertEquals(DarkPalette.surface, untouched.surface)
    }

    /** Palet gelap harus hitam/biru/putih, bukan abu-abu netral seperti basis Mochy. */
    @Test
    fun darkPalette_isStillBlackBlueAndWhite() {
        assertEquals(BlossomColors.Black, DarkPalette.background)
        assertEquals(BlossomColors.White, DarkPalette.onBackground)
        assertEquals(BlossomColors.Blue, DarkPalette.primary)
        assertTrue("aksen harus biru", BlossomColors.Blue.blue > BlossomColors.Blue.red)
        assertTrue("aksen harus biru", BlossomColors.Blue.blue > BlossomColors.Blue.green)
        assertEquals(BlossomColors.Azure, DefaultThemeColor)
    }

    /**
     * Mode terang memakai palet es/azure/kartu putih.
     *
     * Azure mockup #2C7BE5 gagal AA di halaman es (3.68) dan dengan teks putih
     * (4.14), jadi yang dirender #2368D8 (4.62 / 5.19). Nilai spec tetap
     * disimpan di [BlossomColors.AzureSpec] sebagai rujukan desain.
     *
     * Palet ini dirender saat perangkat dalam mode terang (`ForceDarkTheme` = false).
     */
    @Test
    fun theme_followsSystem() {
        // dijaga: kalau ada yang memaku ulang ke true, mode terang mati tanpa error
        assertFalse(ForceDarkTheme)
    }

    @Test
    fun lightPalette_isAzureOnIce() {
        assertEquals(Color(0xFF2368D8), LightPalette.primary)
        assertEquals(Color(0xFFDCEBFF), LightPalette.primaryContainer)
        assertEquals(Color(0xFF0B2A5B), LightPalette.onPrimaryContainer)
        assertEquals(Color(0xFFE8F3FE), LightPalette.background)      // halaman es
        assertEquals(Color(0xFF0E1B2C), LightPalette.onBackground)    // tinta
        assertEquals(Color(0xFFD9E9FA), LightPalette.surfaceVariant)  // panel es
        assertEquals(Color(0xFFCFE3F7), LightPalette.outline)
        assertEquals(Color(0xFFFFFFFF), LightPalette.surface)         // kartu putih
        assertEquals(Color(0xFF3F5B78), LightPalette.onSurfaceVariant)
        // Abu spec lama harus tetap ada di kode, bukan hilang tanpa jejak.
        assertEquals(Color(0xFF8E8E93), BlossomColors.SpecGray)
        assertEquals(Color(0xFF2C7BE5), BlossomColors.AzureSpec)
    }

    /**
     * Di mode terang kartu duduk di atas halaman es sementara panel lebih gelap
     * dari halaman; di mode gelap kartu lebih terang dari latarnya. Dibalik,
     * kartunya hilang dan layarnya kelihatan rusak - bukan sekadar beda gaya.
     */
    @Test
    fun surfaces_stepAwayFromThePage_notIntoIt() {
        val page = LightPalette.background.luminance()
        assertTrue(
            "surfaceContainer harus lebih gelap dari background",
            LightPalette.surfaceContainer.luminance() < page
        )
        assertTrue(
            "surfaceContainerHigh harus lebih gelap dari surfaceContainer",
            LightPalette.surfaceContainerHigh.luminance() < LightPalette.surfaceContainer.luminance()
        )
        assertTrue(
            "surfaceVariant harus lebih gelap dari background",
            LightPalette.surfaceVariant.luminance() < page
        )
        assertTrue(
            "kartu putih harus lebih terang dari halaman es",
            LightPalette.surface.luminance() > page
        )
        assertTrue(
            "kartu mode gelap harus lebih terang dari latarnya",
            DarkPalette.surfaceVariant.luminance() > DarkPalette.background.luminance()
        )
    }

    /** Warna error bukan aksen: teks error harus terbaca di atas container-nya. */
    @Test
    fun lightError_isReadable() {
        assertReadable(LightPalette.error, LightPalette.background, "error di latar")
        assertReadable(LightPalette.onErrorContainer, LightPalette.errorContainer, "teks di error-container")
    }
}
