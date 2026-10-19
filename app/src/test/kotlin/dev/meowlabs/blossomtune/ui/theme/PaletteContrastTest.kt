package dev.meowlabs.blossomtune.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Palet hitam/biru/putih dikunci di sini.
 *
 * Warna aksen gampang diganti diam-diam lewat satu commit kecil, dan hasilnya baru
 * kelihatan sebagai teks yang tidak terbaca di HP orang. Tes ini menahan itu: setiap
 * pasangan teks/latar harus lolos WCAG AA (>= 4.5) sebelum boleh masuk rilis.
 *
 * Dua palet diuji, bukan satu: yang dipakai sekarang memang gelap
 * (`ForceDarkTheme`), tapi palet terang tetap ada di kode dan tetap harus benar.
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
    }

    @Test
    fun lightPalette_isReadableOnEverySurface() {
        assertReadable(LightPalette.onBackground, LightPalette.background, "teks di latar")
        assertReadable(LightPalette.onSurface, LightPalette.surface, "teks di surface")
        assertReadable(LightPalette.onSurfaceVariant, LightPalette.surfaceVariant, "teks di kartu")
        assertReadable(LightPalette.primary, LightPalette.background, "aksen biru di latar putih")
        assertReadable(LightPalette.onPrimary, LightPalette.primary, "teks di tombol biru")
        assertReadable(LightPalette.onPrimaryContainer, LightPalette.primaryContainer, "teks di container")
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

    /** Palet gelap harus tetap hitam/biru/putih, bukan abu-abu netral seperti sebelumnya. */
    @Test
    fun darkPalette_isStillBlackBlueAndWhite() {
        assertEquals(BlossomColors.Black, DarkPalette.background)
        assertEquals(BlossomColors.White, DarkPalette.onBackground)
        assertEquals(BlossomColors.Blue, DarkPalette.primary)
        assertTrue("aksen harus biru", BlossomColors.Blue.blue > BlossomColors.Blue.red)
        assertTrue("aksen harus biru", BlossomColors.Blue.blue > BlossomColors.Blue.green)
        assertEquals(BlossomColors.Navy, DefaultThemeColor)
    }

    /**
     * Mode terang memakai palet navy/abu/putih, dan itu yang benar-benar dirender
     * (`ForceDarkTheme` = false).
     *
     * Abu spec #8E8E93 (3.26 di atas putih) gagal WCAG AA, jadi yang dirender
     * #73737B (4.70). Nilai spec tetap disimpan di `BlossomColors.SpecGray`
     * supaya rujukannya tidak hilang.
     */
    @Test
    fun lightPalette_isNavyOnWhite() {
        assertEquals(false, ForceDarkTheme)
        assertEquals(Color(0xFF1C2144), LightPalette.primary)        // navy spec, lolos AA tanpa digelapkan
        assertEquals(Color(0xFFF2F2F7), LightPalette.primaryContainer)
        assertEquals(Color(0xFF1C2144), LightPalette.onPrimaryContainer)
        assertEquals(Color(0xFFFFFFFF), LightPalette.background)     // putih
        assertEquals(Color(0xFF1C2144), LightPalette.onBackground)   // navy
        assertEquals(Color(0xFFF2F2F7), LightPalette.surfaceVariant) // panel abu
        assertEquals(Color(0xFFE5E5EA), LightPalette.outline)
        assertEquals(Color(0xFFFFFFFF), LightPalette.surface)        // kartu putih
        assertEquals(Color(0xFF6D6D77), LightPalette.onSurfaceVariant) // abu spec #8E8E93, digelapkan utk AA
        // Spec asli harus tetap ada di kode, bukan hilang tanpa jejak.
        assertEquals(Color(0xFF8E8E93), BlossomColors.SpecGray)
    }

    /**
     * Di mode terang kartu harus lebih gelap dari halaman; di mode gelap lebih
     * terang. Dibalik, kartunya hilang dan layarnya kelihatan rusak - bukan
     * sekadar beda gaya.
     */
    @Test
    fun lightSurfaces_sitBelowThePage_neverAbove() {
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
