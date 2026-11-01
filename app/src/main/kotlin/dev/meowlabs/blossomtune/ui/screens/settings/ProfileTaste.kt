// ProfileTaste.kt
//
// Aturan data untuk Edit profile, tanpa Compose supaya bisa diuji di unit test:
// nama tampilan (dirapikan dan dibatasi panjangnya) dan musik kesukaan (genre
// pilihan + artis + lagu). Genre disimpan sebagai satu string dipisah koma di
// DataStore (FavoriteGenresKey).

package dev.meowlabs.blossomtune.ui.screens.settings

object ProfileTaste {
    /** Batas panjang nama, cukup untuk muat di kartu ID tanpa terpotong parah. */
    const val MaxNameLength = 24

    /** Batas panjang teks artis / lagu favorit. */
    const val MaxFavoriteLength = 60

    /** Batas jumlah genre yang boleh dipilih sekaligus. */
    const val MaxGenres = 5

    /** Pilihan genre di layar Edit profile. */
    val GenreChoices: List<String> = listOf(
        "Pop", "Rock", "Hip-hop", "R&B", "Jazz", "Indie", "EDM", "K-pop",
        "J-pop", "Dangdut", "Lo-fi", "Klasik", "Metal", "Akustik", "Reggae",
    )

    /** Hasil pemeriksaan nama sebelum disimpan. */
    sealed interface NameCheck {
        data class Ok(val name: String) : NameCheck
        data object Empty : NameCheck
        data object TooLong : NameCheck
    }

    /** Spasi di ujung dibuang, spasi berderet dijadikan satu. */
    fun cleanName(raw: String): String = raw.trim().replace(Regex("\\s+"), " ")

    fun checkName(raw: String): NameCheck {
        val name = cleanName(raw)
        return when {
            name.isEmpty() -> NameCheck.Empty
            name.length > MaxNameLength -> NameCheck.TooLong
            else -> NameCheck.Ok(name)
        }
    }

    /** Teks artis / lagu: dirapikan dan dipotong ke batas panjang. */
    fun cleanFavorite(raw: String): String = cleanName(raw).take(MaxFavoriteLength)

    /** "Pop, Rock,,jazz" -> [Pop, Rock, jazz]; kosong dan duplikat (tanpa beda huruf besar) dibuang. */
    fun parseGenres(stored: String): List<String> =
        stored.split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinctBy { it.lowercase() }
            .take(MaxGenres)

    fun serializeGenres(genres: Collection<String>): String =
        genres.map { it.trim() }
            .filter { it.isNotEmpty() && ',' !in it }
            .distinctBy { it.lowercase() }
            .take(MaxGenres)
            .joinToString(",")

    /**
     * Pilih atau batalkan satu genre. Genre baru ditolak (daftar tidak berubah)
     * kalau sudah [MaxGenres] yang terpilih.
     */
    fun toggleGenre(current: List<String>, genre: String): List<String> = when {
        current.any { it.equals(genre, ignoreCase = true) } -> current.filterNot { it.equals(genre, ignoreCase = true) }
        current.size >= MaxGenres -> current
        else -> current + genre
    }

    /** Ringkasan satu baris untuk kartu di halaman profil, atau null kalau belum diisi apa-apa. */
    fun summary(genres: List<String>, artist: String, song: String): String? {
        val parts = buildList {
            if (genres.isNotEmpty()) add(genres.joinToString(" · "))
            if (artist.isNotBlank()) add("Artis: $artist")
            if (song.isNotBlank()) add("Lagu: $song")
        }
        return parts.takeIf { it.isNotEmpty() }?.joinToString("\n")
    }
}
