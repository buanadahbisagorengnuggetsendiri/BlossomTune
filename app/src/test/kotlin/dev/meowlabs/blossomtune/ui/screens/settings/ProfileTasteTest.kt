package dev.meowlabs.blossomtune.ui.screens.settings

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileTasteTest {

    @Test
    fun nameIsTrimmedAndSpacesCollapsed() {
        assertEquals(ProfileTaste.NameCheck.Ok("Buana Putra"), ProfileTaste.checkName("  Buana    Putra "))
    }

    @Test
    fun blankNameIsRejected() {
        assertEquals(ProfileTaste.NameCheck.Empty, ProfileTaste.checkName("   "))
    }

    @Test
    fun tooLongNameIsRejected() {
        val long = "a".repeat(ProfileTaste.MaxNameLength + 1)
        assertEquals(ProfileTaste.NameCheck.TooLong, ProfileTaste.checkName(long))
    }

    @Test
    fun nameAtLimitIsAccepted() {
        val exact = "a".repeat(ProfileTaste.MaxNameLength)
        assertTrue(ProfileTaste.checkName(exact) is ProfileTaste.NameCheck.Ok)
    }

    @Test
    fun genresRoundTrip() {
        val genres = listOf("Pop", "Rock", "Jazz")
        assertEquals(genres, ProfileTaste.parseGenres(ProfileTaste.serializeGenres(genres)))
    }

    @Test
    fun parseDropsBlanksAndDuplicates() {
        assertEquals(listOf("Pop", "Rock"), ProfileTaste.parseGenres(" Pop, ,Rock,pop,"))
    }

    @Test
    fun emptyStoredValueGivesNoGenres() {
        assertEquals(emptyList<String>(), ProfileTaste.parseGenres(""))
    }

    @Test
    fun toggleAddsAndRemoves() {
        val added = ProfileTaste.toggleGenre(listOf("Pop"), "Rock")
        assertEquals(listOf("Pop", "Rock"), added)
        assertEquals(listOf("Rock"), ProfileTaste.toggleGenre(added, "pop"))
    }

    @Test
    fun toggleRefusesBeyondLimit() {
        val full = ProfileTaste.GenreChoices.take(ProfileTaste.MaxGenres)
        assertEquals(full, ProfileTaste.toggleGenre(full, "Reggae"))
    }

    @Test
    fun favoriteIsCutToLimit() {
        val long = "x".repeat(ProfileTaste.MaxFavoriteLength + 20)
        assertEquals(ProfileTaste.MaxFavoriteLength, ProfileTaste.cleanFavorite(long).length)
    }

    @Test
    fun summaryIsNullWhenNothingFilled() {
        assertNull(ProfileTaste.summary(emptyList(), "", " "))
    }

    @Test
    fun summaryListsEverythingFilled() {
        assertEquals(
            "Pop · Indie\nArtis: Hindia\nLagu: Evaluasi",
            ProfileTaste.summary(listOf("Pop", "Indie"), "Hindia", "Evaluasi"),
        )
    }
}
