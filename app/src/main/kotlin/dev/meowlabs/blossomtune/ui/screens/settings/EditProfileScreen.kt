// EditProfileScreen.kt
//
// "Edit profile": ganti nama tampilan (dipakai kartu ID, sapaan, dan recap) dan
// isi musik kesukaan - sampai lima genre, satu artis, satu lagu. Semua disimpan
// di DataStore lewat rememberPreference; aturan datanya ada di ProfileTaste.
// Nama baru hanya tersimpan saat tombol Simpan ditekan, jadi keluar tanpa
// menyimpan tidak mengubah apa pun.

package dev.meowlabs.blossomtune.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.meowlabs.blossomtune.LocalPlayerAwareWindowInsets
import dev.meowlabs.blossomtune.R
import dev.meowlabs.blossomtune.constants.FavoriteArtistKey
import dev.meowlabs.blossomtune.constants.FavoriteGenresKey
import dev.meowlabs.blossomtune.constants.FavoriteSongKey
import dev.meowlabs.blossomtune.constants.UsernameKey
import dev.meowlabs.blossomtune.ui.theme.InterFontFamily
import dev.meowlabs.blossomtune.ui.utils.stableSystemBars
import dev.meowlabs.blossomtune.utils.rememberPreference

const val EditProfileRoute = "edit_profile"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val (storedName, setStoredName) = rememberPreference(UsernameKey, "")
    val (storedGenres, setStoredGenres) = rememberPreference(FavoriteGenresKey, "")
    val (storedArtist, setStoredArtist) = rememberPreference(FavoriteArtistKey, "")
    val (storedSong, setStoredSong) = rememberPreference(FavoriteSongKey, "")

    // draf lokal; baru ditulis ke DataStore saat Simpan
    var name by rememberSaveable { mutableStateOf(storedName) }
    // List bukan tipe Bundle yang pasti aman, jadi genre memakai remember biasa
    var genres by remember { mutableStateOf(ProfileTaste.parseGenres(storedGenres)) }
    var artist by rememberSaveable { mutableStateOf(storedArtist) }
    var song by rememberSaveable { mutableStateOf(storedSong) }
    var nameError by remember { mutableStateOf<String?>(null) }

    val topInset = WindowInsets.stableSystemBars.asPaddingValues().calculateTopPadding()
    val bottomInset = LocalPlayerAwareWindowInsets.current.asPaddingValues().calculateBottomPadding()

    fun save() {
        when (val check = ProfileTaste.checkName(name)) {
            ProfileTaste.NameCheck.Empty -> nameError = "Nama tidak boleh kosong"
            ProfileTaste.NameCheck.TooLong -> nameError = "Maksimal ${ProfileTaste.MaxNameLength} karakter"
            is ProfileTaste.NameCheck.Ok -> {
                setStoredName(check.name)
                setStoredGenres(ProfileTaste.serializeGenres(genres))
                setStoredArtist(ProfileTaste.cleanFavorite(artist))
                setStoredSong(ProfileTaste.cleanFavorite(song))
                android.widget.Toast.makeText(context, "Profil disimpan", android.widget.Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, end = 20.dp, top = topInset + 24.dp, bottom = bottomInset + 40.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { navController.navigateUp() },
            ) {
                Icon(
                    painterResource(R.drawable.arrow_back),
                    contentDescription = "Kembali",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp),
                )
            }
            Text(
                "Edit profile",
                style = TitleStyle.copy(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.padding(start = 14.dp),
            )
        }

        Spacer(Modifier.height(28.dp))
        SectionLabel("Nama profil")
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it.take(ProfileTaste.MaxNameLength + 8)
                nameError = null
            },
            singleLine = true,
            isError = nameError != null,
            supportingText = {
                Text(nameError ?: "${ProfileTaste.cleanName(name).length}/${ProfileTaste.MaxNameLength}")
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(20.dp))
        SectionLabel("Genre favorit (maks. ${ProfileTaste.MaxGenres})")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            ProfileTaste.GenreChoices.forEach { genre ->
                val selected = genres.any { it.equals(genre, ignoreCase = true) }
                FilterChip(
                    selected = selected,
                    onClick = { genres = ProfileTaste.toggleGenre(genres, genre) },
                    enabled = selected || genres.size < ProfileTaste.MaxGenres,
                    label = { Text(genre) },
                    leadingIcon = if (selected) {
                        { Icon(painterResource(R.drawable.check), contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else {
                        null
                    },
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Artis favorit")
        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it.take(ProfileTaste.MaxFavoriteLength) },
            singleLine = true,
            placeholder = { Text("mis. Hindia") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))
        SectionLabel("Lagu favorit")
        OutlinedTextField(
            value = song,
            onValueChange = { song = it.take(ProfileTaste.MaxFavoriteLength) },
            singleLine = true,
            placeholder = { Text("mis. Evaluasi") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(32.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(27.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { save() },
        ) {
            Text("Simpan", style = TitleStyle.copy(fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        style = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier.padding(start = 2.dp, bottom = 8.dp),
    )
}

private val TitleStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    letterSpacing = (-0.5).sp,
)
