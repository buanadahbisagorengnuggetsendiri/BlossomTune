package dev.meowlabs.blossomtune.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Palet petal/deep rose (terang) dan malam plum/blossom/petal (gelap) dikunci di sini.
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
        assertReadable(DarkPalette.primary, DarkPalette.background, "aksen blossom di latar malam")
        assertReadable(DarkPalette.onPrimary, DarkPalette.primary, "teks di tombol blossom")
        assertReadable(DarkPalette.onPrimaryContainer, DarkPalette.primaryContainer, "teks di container")
        assertReadable(DarkPalette.onSecondaryContainer, DarkPalette.secondaryContainer, "teks di container kedua")
        assertReadable(DarkPalette.error, DarkPalette.background, "error di latar malam")
    }

    @Test
    fun lightPalette_isReadableOnEverySurface() {
        assertReadable(LightPalette.onBackground, LightPalette.background, "teks di latar")
        assertReadable(LightPalette.onSurface, LightPalette.surface, "teks di surface")
        assertReadable(LightPalette.onSurfaceVariant, LightPalette.surfaceVariant, "teks di kartu")
        assertReadable(LightPalette.primary, LightPalette.background, "aksen deep rose di halaman petal")
        assertReadable(LightPalette.onPrimary, LightPalette.primary, "teks di tombol deep rose")
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

    /** Palet gelap harus malam plum/blossom/petal, bukan abu-abu netral seperti basis Mochy. */
    @Test
    fun darkPalette_isStillNightBlossomAndPetal() {
        assertEquals(BlossomColors.Night, DarkPalette.background)
        assertEquals(BlossomColors.Petal, DarkPalette.onBackground)
        assertEquals(BlossomColors.Blossom, DarkPalette.primary)
        assertTrue("aksen harus pink", BlossomColors.Blossom.red > BlossomColors.Blossom.blue)
        assertTrue("aksen harus pink", BlossomColors.Blossom.red > BlossomColors.Blossom.green)
        assertEquals(BlossomColors.DeepRose, DefaultThemeColor)
    }

    /**
     * Mode terang memakai palet petal/deep rose/kartu putih.
     *
     * Rose palet #C84870 gagal AA sebagai teks kecil di halaman petal (3.91),
     * jadi yang dirender #9A3050 (6.20 / 7.21). Nilai palet tetap
     * disimpan di [BlossomColors.RoseSpec] sebagai rujukan desain.
     *
     * Palet ini dirender saat perangkat dalam mode terang (`ForceDarkTheme` = false).
     */
    @Test
    fun theme_followsSystem() {
        // dijaga: kalau ada yang memaku ulang ke true, mode terang mati tanpa error
        assertFalse(ForceDarkTheme)
    }

    @Test
    fun lightPalette_isDeepRoseOnPetal() {
        assertEquals(Color(0xFF9A3050), LightPalette.primary)
        assertEquals(Color(0xFFFADADD), LightPalette.primaryContainer)
        assertEquals(Color(0xFF4A1424), LightPalette.onPrimaryContainer)
        assertEquals(Color(0xFFFAEAEE), LightPalette.background)      // halaman petal
        assertEquals(Color(0xFF2A121B), LightPalette.onBackground)    // tinta
        assertEquals(Color(0xFFF6DCE1), LightPalette.surfaceVariant)  // panel petal
        assertEquals(Color(0xFFE8C6CF), LightPalette.outline)
        assertEquals(Color(0xFFFFFFFF), LightPalette.surface)         // kartu putih
        assertEquals(Color(0xFF5E3342), LightPalette.onSurfaceVariant)
        // Abu spec lama harus tetap ada di kode, bukan hilang tanpa jejak.
        assertEquals(Color(0xFF8E8E93), BlossomColors.SpecGray)
        assertEquals(Color(0xFFC84870), BlossomColors.RoseSpec)
    }

    /**
     * Di mode terang kartu duduk di atas halaman petal sementara panel lebih gelap
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
            "kartu putih harus lebih terang dari halaman petal",
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
    /**
     * Tinta sheet (info sheets, menu, larp, settings) di atas permukaan sheet/row
     * yang ikut mode. Dulu hardcode putih di atas sheet putih = kontras 1.00,
     * dan #B3B3B3 di atas putih = 2.10. Tinta sheet sekarang ikut mode lewat
     * [LightInk]/[DarkInk], pemutar lewat [BlossomColors.Ink] (terang) dan putih
     * (gelap); semuanya harus lolos AA di kedua mode.
     */
    @Test
    fun sheetInk_isReadableInBothModes() {
        assertReadable(LightInk.primary, LightSurfaces.sheet, "tinta sheet terang di sheet putih")
        assertReadable(LightInk.primary, LightSurfaces.row, "tinta sheet terang di row petal muda")
        assertReadable(LightInk.muted, LightSurfaces.sheet, "tinta redup terang di sheet putih")
        assertReadable(LightInk.muted, LightSurfaces.row, "tinta redup terang di row petal muda")
        assertReadable(DarkInk.primary, DarkSurfaces.sheet, "tinta sheet gelap di sheet plum")
        assertReadable(DarkInk.primary, DarkSurfaces.row, "tinta sheet gelap di row plum")
        assertReadable(DarkInk.muted, DarkSurfaces.sheet, "tinta redup gelap di sheet plum")
        assertReadable(DarkInk.muted, DarkSurfaces.row, "tinta redup gelap di row plum")
        assertReadable(LightInk.primary, LightPalette.background, "tinta pemutar terang di halaman petal")
        assertReadable(BlossomColors.White, DarkPalette.background, "tinta pemutar gelap di latar malam")
    }

}
