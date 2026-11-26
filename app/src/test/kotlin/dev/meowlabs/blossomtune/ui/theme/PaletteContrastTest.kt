package dev.meowlabs.blossomtune.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import dev.meowlabs.blossomtune.ui.screens.audiolab.LabColors
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

    /** Teks besar (lirik): ambang WCAG AA untuk teks besar adalah 3:1, bukan 4.5. */
    private fun assertLargeTextReadable(fg: Color, bg: Color, where: String) {
        val r = contrast(fg, bg)
        assertTrue("$where: kontras %.2f < 3.0".format(r), r >= 3.0)
    }

    /** Baris yang sudah lewat boleh pudar, tapi jangan sampai hilang total. */
    private fun assertFadedButPresent(fg: Color, bg: Color, where: String) {
        val r = contrast(fg, bg)
        assertTrue("$where: kontras %.2f < 1.5".format(r), r >= 1.5)
    }

    /**
     * `Color.compositeOver` tidak ada di versi Compose proyek ini, jadi blend
     * dihitung sendiri: fg di atas bg dengan alpha fg.
     */
    private fun blendOver(fg: Color, bg: Color): Color {
        val a = fg.alpha
        return Color(
            red = fg.red * a + bg.red * (1 - a),
            green = fg.green * a + bg.green * (1 - a),
            blue = fg.blue * a + bg.blue * (1 - a),
            alpha = 1f,
        )
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


    /**
     * Pill/baris kualitas di layar Audio Quality gelap di kedua mode, jadi deskripsinya
     * tidak boleh pakai Secondary yang ikut mode (Deep Rose di terang = 2.62 di atas
     * #111113). [LabColors.OnDark] dipaku terang dan harus lolos AA di semua latar
     * pill yang dipakai: #111113, #2A2A2E (terpilih), #242428 (baris terangkat).
     */
    @Test
    fun darkQualityPills_keepDescriptionReadableInBothModes() {
        assertReadable(LabColors.OnDark, Color(0xFF111113), "deskripsi quality di pill gelap")
        assertReadable(LabColors.OnDark, Color(0xFF2A2A2E), "deskripsi quality di pill terpilih")
        assertReadable(LabColors.OnDark, Color(0xFF242428), "status provider di baris terangkat")
    }

    /**
     * Kartu Top Result di Search gelap di kedua mode (artwork blur + scrim hitam
     * di atas Tile), jadi judul/detailnya dipaku terang dan tidak boleh ikut mode.
     * Latar paling terang yang bisa muncul di belakang baris teks adalah artwork
     * putih polos di bawah scrim yang diperkuat (0.72 di 0.55, 0.92 di bawah):
     * #363636 di baris judul. Kasus nyata yang dilaporkan ("Dancin'") latarnya
     * #862D48 dengan scrim lama, dan #612134 dengan scrim baru. Petal dan OnDark
     * harus lolos AA di keduanya.
     */
    @Test
    fun topResultCard_staysReadableOnDarkArtworkCard() {
        assertReadable(BlossomColors.Petal, Color(0xFF363636), "judul Top Result di artwork putih")
        assertReadable(LabColors.OnDark, Color(0xFF363636), "detail Top Result di artwork putih")
        assertReadable(BlossomColors.Petal, Color(0xFF612134), "judul Top Result di artwork Dancin'")
        assertReadable(LabColors.OnDark, Color(0xFF612134), "detail Top Result di artwork Dancin'")
    }

    /**
     * 0.6.6: baris judul player TRANSPARAN (scrim Night dihapus atas permintaan
     * Arya, artwork kelihatan utuh di belakang judul). Tidak ada latar yang
     * dijamin lagi, jadi tes ini tidak mengklaim kontras di atas Night seperti
     * 0.6.5; yang dikunci adalah paletnya tetap terang di kedua mode (judul
     * Petal, artis Petal 0.7, hati liked Blossom, hati belum-like Petal 0.85),
     * dan pasangan itu tetap lolos AA di atas artwork gelap sebagai wakil
     * latar gelap. Di atas artwork sangat terang teks putih memang lebih
     * susah dibaca, itu konsekuensi yang diminta, bukan bug.
     */
    @Test
    fun playerTitleRow_staysLightOnTransparentRowInBothModes() {
        // Artwork gelap sebagai wakil latar gelap; bukan Night, itu sudah tidak ada.
        val darkArtwork = Color(0xFF241A16)
        assertReadable(BlossomColors.Petal, darkArtwork, "judul player di artwork gelap")
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.7f), darkArtwork),
            darkArtwork,
            "artis player di artwork gelap",
        )
        assertReadable(BlossomColors.Blossom, darkArtwork, "hati liked di artwork gelap")
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.85f), darkArtwork),
            darkArtwork,
            "hati belum-like di artwork gelap",
        )
    }

    /**
     * 0.6.5: lirik duduk di atas halaman Petal (terang) / Night (gelap). Baris aktif
     * tinta primer penuh; baris berikut primer 0.65 (segera) dan 0.55 (nanti),
     * teks besar jadi ambangnya 3:1.
     *
     * Ini menguji salinan polosnya (plain copy), bukan efek soft-light: halaman lirik
     * selalu berjalan di jalur soft-lit (blend != null), dan soft-light putih di atas
     * latar terang hanya memberi 1.1:1 - perbaikan pertama yang hanya menyentuh jalur
     * tanpa blend tidak pernah sampai ke halaman ini. Salinan polos di tinta primer
     * pada alpha istirahatnya adalah jaminan kontrasnya; salinan soft di bawahnya
     * hanya mewarnai cover. Baris lampau di 0.24 hanya dijamin hadir, bukan terbaca.
     */
    @Test
    fun lyricsLines_areReadableInBothModes() {
        assertReadable(BlossomColors.Ink, BlossomColors.Petal, "lirik aktif di halaman terang")
        assertReadable(BlossomColors.Petal, BlossomColors.Night, "lirik aktif di halaman gelap")
        assertLargeTextReadable(
            blendOver(BlossomColors.Ink.copy(alpha = 0.65f), BlossomColors.Petal),
            BlossomColors.Petal,
            "lirik berikut di halaman terang",
        )
        assertLargeTextReadable(
            blendOver(BlossomColors.Ink.copy(alpha = 0.55f), BlossomColors.Petal),
            BlossomColors.Petal,
            "lirik nanti di halaman terang",
        )
        assertLargeTextReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.65f), BlossomColors.Night),
            BlossomColors.Night,
            "lirik berikut di halaman gelap",
        )
        assertLargeTextReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.55f), BlossomColors.Night),
            BlossomColors.Night,
            "lirik nanti di halaman gelap",
        )
        assertLargeTextReadable(
            blendOver(BlossomColors.Ink.copy(alpha = 0.5f), BlossomColors.Petal),
            BlossomColors.Petal,
            "kata belum dinyanyikan di baris aktif terang",
        )
        assertFadedButPresent(
            blendOver(BlossomColors.Ink.copy(alpha = 0.24f), BlossomColors.Petal),
            BlossomColors.Petal,
            "lirik lampau di halaman terang",
        )
        assertFadedButPresent(
            blendOver(BlossomColors.Petal.copy(alpha = 0.24f), BlossomColors.Night),
            BlossomColors.Night,
            "lirik lampau di halaman gelap",
        )
    }

    /** Titik jeda antar baris lirik: tinta primer juga, bukan putih. */
    @Test
    fun lyricsInterludeDots_areReadableInBothModes() {
        assertReadable(
            blendOver(BlossomColors.Ink.copy(alpha = 0.75f), BlossomColors.Petal),
            BlossomColors.Petal,
            "titik jeda lirik di halaman terang",
        )
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.75f), BlossomColors.Night),
            BlossomColors.Night,
            "titik jeda lirik di halaman gelap",
        )
    }

    /**
     * 0.6.5: menu player, tombol transport, baris Next Song, dan kartu Turntable
     * semuanya duduk di atas latar gelap (foto blur / kartu hitam), jadi tintanya
     * Petal di kedua mode.
     */
    @Test
    fun playerControlsOnDarkArtwork_areReadable() {
        assertReadable(BlossomColors.Petal, BlossomColors.Night, "label menu di atas foto gelap")
        assertReadable(BlossomColors.Petal, BlossomColors.Night, "judul Turntable di atas kartu gelap")
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.65f), BlossomColors.Night),
            BlossomColors.Night,
            "artis Turntable di atas kartu gelap",
        )
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.7f), BlossomColors.Night),
            BlossomColors.Night,
            "label Next Song di atas foto gelap",
        )
        assertReadable(
            blendOver(BlossomColors.Petal.copy(alpha = 0.55f), BlossomColors.Night),
            BlossomColors.Night,
            "judul Next Song di atas foto gelap",
        )
        assertReadable(
            BlossomColors.Ink,
            blendOver(BlossomColors.Petal.copy(alpha = 0.92f), BlossomColors.Night),
            "teks hold badge di atas pill Petal",
        )
        assertReadable(BlossomColors.Petal, Color(0xFF262626), "tombol Edit/Set di pemilih gaya")
    }

    /**
     * 0.6.7: ikon kartu aksi sheet lagu dan ikon perangkat duduk di atas baris
     * pink muda terang / gelap, jadi tintanya tinta primer yang ikut mode,
     * bukan putih (putih di atas #F7E6EA hanya 1.20:1).
     */
    @Test
    fun sheetActionCardIcons_areReadableInBothModes() {
        val rowLight = Color(0xFFF7E6EA)
        val rowDark = Color(0xFF221017)
        assertReadable(BlossomColors.Ink, rowLight, "ikon kartu aksi di baris terang")
        assertReadable(BlossomColors.Petal, rowDark, "ikon kartu aksi di baris gelap")
        assertReadable(BlossomColors.Ink, rowLight, "ikon perangkat di baris terang")
        assertReadable(BlossomColors.Petal, rowDark, "ikon perangkat di baris gelap")
    }

    /**
     * 0.6.7: teks penjelasan layar Penyimpanan duduk di atas halaman, bukan di
     * atas kartu gelap, jadi warnanya tinta primer (putih di atas Petal hanya
     * 1.16:1). Judul scaffold ikut: scrim header saat halaman digulir kini
     * putih di mode terang, jadi judul gelap tetap terbaca di atasnya
     * (sebelumnya scrim hitam membuat judul 2.13:1 saat collapsed).
     */
    @Test
    fun storageSectionText_isReadableOnPageInBothModes() {
        assertReadable(BlossomColors.Ink, BlossomColors.Petal, "teks penjelasan Penyimpanan di halaman terang")
        assertReadable(BlossomColors.Petal, BlossomColors.Night, "teks penjelasan Penyimpanan di halaman gelap")
        // Scrim header collapsed: putih 0.90 di atas Petal (terang), hitam di atas Night (gelap).
        assertReadable(
            BlossomColors.Ink,
            blendOver(Color.White.copy(alpha = 0.90f), BlossomColors.Petal),
            "judul Penyimpanan di atas scrim header terang",
        )
        assertReadable(
            BlossomColors.Petal,
            blendOver(Color.Black.copy(alpha = 0.90f), BlossomColors.Night),
            "judul Penyimpanan di atas scrim header gelap",
        )
    }

    /**
     * 0.6.7: judul seksi Modul sempat abu-abu hardcoded 0xFFB4B4B8 (1.78:1 di
     * atas Petal), kini tinta primer. Status modul tidak diubah: teksnya
     * putih 0.85 di atas kartu gelap dan sudah lolos.
     */
    @Test
    fun moduleSectionTitles_areReadableInBothModes() {
        assertReadable(BlossomColors.Ink, BlossomColors.Petal, "judul seksi Modul di halaman terang")
        assertReadable(BlossomColors.Petal, BlossomColors.Night, "judul seksi Modul di halaman gelap")
        val moduleRow = Color(0xFF131314)
        assertReadable(
            blendOver(Color.White.copy(alpha = 0.85f), moduleRow),
            moduleRow,
            "status modul di atas kartu gelap",
        )
    }

    /**
     * 0.6.7: tombol play dan next mini player sempat memakai onSurface, yang
     * di mode terang adalah tinta gelap (1.03:1 di atas bar #171717). Barnya
     * gelap di kedua mode, jadi glyphnya putih, sama seperti previous.
     * Judul dan artisnya sudah putih / putih 0.72 dan tidak diubah.
     */
    @Test
    fun miniPlayerTransport_isReadableOnDarkBar() {
        val miniBar = Color(0xFF171717)
        assertReadable(Color.White, miniBar, "tombol play/next mini player di atas bar gelap")
        assertReadable(Color.White, miniBar, "judul mini player di atas bar gelap")
        assertReadable(
            blendOver(Color.White.copy(alpha = 0.72f), miniBar),
            miniBar,
            "artis mini player di atas bar gelap",
        )
    }
}

