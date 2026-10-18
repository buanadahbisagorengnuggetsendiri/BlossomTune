// NavigationBuilder.kt

package dev.meowlabs.blossomtune.ui.screens

import android.app.Activity
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import dev.meowlabs.blossomtune.db.entities.FormatEntity
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import dev.meowlabs.blossomtune.R
import dev.meowlabs.blossomtune.LocalDatabase
import dev.meowlabs.blossomtune.constants.DarkModeKey
import dev.meowlabs.blossomtune.ui.theme.ForceDarkTheme
import dev.meowlabs.blossomtune.constants.PlaylistSortType
import dev.meowlabs.blossomtune.constants.PureBlackKey
import dev.meowlabs.blossomtune.db.entities.ArtistEntity
import dev.meowlabs.blossomtune.db.entities.SongArtistMap
import dev.meowlabs.blossomtune.db.entities.SongEntity
import dev.meowlabs.blossomtune.ui.component.ContainerCloseEasing
import dev.meowlabs.blossomtune.ui.component.ContainerCloseMillis
import dev.meowlabs.blossomtune.ui.component.ContainerOpenDamping
import dev.meowlabs.blossomtune.ui.component.ContainerOpenStiffness
import dev.meowlabs.blossomtune.ui.component.LocalNavAnimatedContentScope
import dev.meowlabs.blossomtune.ui.component.NavigationTitle
import dev.meowlabs.blossomtune.ui.screens.artist.ArtistAlbumsScreen
import dev.meowlabs.blossomtune.ui.screens.artist.ArtistItemsScreen
import dev.meowlabs.blossomtune.ui.screens.artist.ArtistScreen
import dev.meowlabs.blossomtune.ui.screens.artist.ArtistSongsScreen
import dev.meowlabs.blossomtune.ui.screens.playlist.AutoPlaylistScreen
import dev.meowlabs.blossomtune.ui.screens.playlist.CachePlaylistScreen
import dev.meowlabs.blossomtune.ui.screens.playlist.LocalPlaylistScreen
import dev.meowlabs.blossomtune.ui.screens.playlist.OnlinePlaylistScreen
import dev.meowlabs.blossomtune.ui.screens.playlist.TopPlaylistScreen
import dev.meowlabs.blossomtune.ui.screens.search.GenreScreen
import dev.meowlabs.blossomtune.ui.screens.search.OnlineSearchResult
import dev.meowlabs.blossomtune.ui.screens.search.SearchScreen
import dev.meowlabs.blossomtune.utils.rememberEnumPreference
import dev.meowlabs.blossomtune.utils.rememberPreference
import dev.meowlabs.blossomtune.ui.screens.settings.ProfileScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryAlbumsScreen
import dev.meowlabs.blossomtune.ui.screens.SectionDetailScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.meowlabs.blossomtune.viewmodels.HomeViewModel
import dev.meowlabs.blossomtune.ui.screens.library.LibraryArtistsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import dev.meowlabs.blossomtune.ui.screens.artist.ArtistSimilarScreen
import dev.meowlabs.blossomtune.ui.screens.audiolab.AudioFxScreen
import dev.meowlabs.blossomtune.ui.screens.audiolab.AudioLabScreen
import dev.meowlabs.blossomtune.ui.screens.audiolab.AudioQualityScreen
import dev.meowlabs.blossomtune.ui.screens.equalizer.EqualizerScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryHomeScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryImportedScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryLocalScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryPlaylistsScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibraryRecentlyAddedScreen
import dev.meowlabs.blossomtune.ui.screens.library.LibrarySongsScreen
import dev.meowlabs.blossomtune.ui.screens.recap.StatsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.AppearanceSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.AudioSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.BlossomTuneSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.CipherSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.CrossmixSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.ExperimentalSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.GeneralSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.PlaybackDiagnosticsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.PlaybackSettingsScreen
import dev.meowlabs.blossomtune.ui.screens.settings.devspace.DeveloperSpaceScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.AccountImportRoutePattern
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.AccountImportScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportProgressRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportProgressScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportProvidersRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportProvidersScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportSyncRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportSyncScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.ImportTuneMyMusicRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.TuneMyMusicImportScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.YouTubeLoginRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.YouTubeLoginScreen
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.YouTubeSyncRoute
import dev.meowlabs.blossomtune.ui.screens.settings.importsync.YouTubeSyncScreen
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModuleRoutePattern
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModuleScreen
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModuleSignInRoutePattern
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModuleSignInScreen
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModulesRoute
import dev.meowlabs.blossomtune.ui.screens.settings.modules.ModulesScreen

private val CoverRoutes = listOf("album/", "local_playlist/", "online_playlist/", "auto_playlist/")

private fun String?.opensFromCover(): Boolean = this != null && CoverRoutes.any { startsWith(it) }

// Home moves on the card's clocks: the open spring going out, the close ease coming back. the
// tiny threshold keeps the spring from snapping the last 1% of the scale at the end.
private val HomeRecedeSpring = spring(
    dampingRatio = ContainerOpenDamping,
    stiffness = ContainerOpenStiffness,
    visibilityThreshold = 0.001f,
)
private val HomeReturnEase = tween<Float>(ContainerCloseMillis, easing = ContainerCloseEasing)

/** Library screens recede like Home when a cover opens from them, and come back on the same clock. */
private fun libraryExitTo(route: String?): androidx.compose.animation.ExitTransition? =
    if (route.opensFromCover()) {
        scaleOut(HomeRecedeSpring, targetScale = 0.92f) + fadeOut(HomeRecedeSpring, targetAlpha = 0.45f)
    } else {
        null
    }

/** coming back from a cover page: the same clock as Home; anything else keeps the default */
private fun coverReturnFrom(route: String?): androidx.compose.animation.EnterTransition? =
    if (route.opensFromCover()) {
        scaleIn(HomeReturnEase, initialScale = 0.92f) + fadeIn(HomeReturnEase, initialAlpha = 0.45f)
    } else {
        null
    }

/**
 * a screen an album or playlist can be opened from (the Library's pages, Search, an artist,
 * Home's section pages): it hands its transition scope to its covers, so the page grows out of
 * the one tapped, and it sinks back while the page is open, exactly as Home does
 */
private fun NavGraphBuilder.coverSourceComposable(
    route: String,
    arguments: List<androidx.navigation.NamedNavArgument> = emptyList(),
    content: @Composable androidx.compose.animation.AnimatedContentScope.(androidx.navigation.NavBackStackEntry) -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    exitTransition = { libraryExitTo(targetState.destination.route) },
    popEnterTransition = { coverReturnFrom(initialState.destination.route) },
) { entry ->
    CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
        content(entry)
    }
}

private fun libraryReturnFrom(route: String?): androidx.compose.animation.EnterTransition =
    if (route.opensFromCover()) {
        scaleIn(HomeReturnEase, initialScale = 0.92f) + fadeIn(HomeReturnEase, initialAlpha = 0.45f)
    } else {
        fadeIn(tween(150))
    }

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.navigationBuilder(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    activity: Activity,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = Screens.Home.route,
        // opening a cover: Home sinks back and dims while the card grows toward you, then comes
        // forward again on the way back, in step with the card so a back swipe scrubs both
        exitTransition = {
            if (targetState.destination.route.opensFromCover()) {
                scaleOut(HomeRecedeSpring, targetScale = 0.92f) + fadeOut(HomeRecedeSpring, targetAlpha = 0.45f)
            } else {
                null
            }
        },
        popEnterTransition = {
            if (initialState.destination.route.opensFromCover()) {
                scaleIn(HomeReturnEase, initialScale = 0.92f) + fadeIn(HomeReturnEase, initialAlpha = 0.45f)
            } else {
                fadeIn(tween(150))
            }
        },
    ) {

        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            HomeScreen(navController = navController, snackbarHostState = snackbarHostState)
        }
    }

    coverSourceComposable(
        route = "section_detail/{sectionId}",
        arguments = listOf(
            navArgument("sectionId") {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val sectionId = backStackEntry.arguments?.getString("sectionId") ?: return@coverSourceComposable

        val homeViewModel: HomeViewModel = hiltViewModel(navController.getBackStackEntry(Screens.Home.route))
        SectionDetailScreen(
            navController = navController,
            sectionId = sectionId,
            homeViewModel = homeViewModel
        )
    }

    coverSourceComposable("history") {
        HistoryScreen(navController = navController)
    }

    composable(
        route = "coming_soon/{sectionTitle}",
        arguments = listOf(
            navArgument("sectionTitle") {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val sectionTitle = backStackEntry.arguments?.getString("sectionTitle") ?: return@composable
        ComingSoonScreen(navController = navController, sectionTitle = sectionTitle)
    }

    coverSourceComposable(Screens.Search.route) {
        val pureBlackEnabled by rememberPreference(PureBlackKey, defaultValue = false)
        val useDarkTheme = ForceDarkTheme
        val pureBlack = remember(pureBlackEnabled, useDarkTheme) {
            pureBlackEnabled && useDarkTheme
        }
        SearchScreen(
            navController = navController,
            pureBlack = pureBlack
        )
    }

    composable(
        route = "library",
        // a playlist opening from its cover: the Library sinks back while it grows, as Home does
        exitTransition = { libraryExitTo(targetState.destination.route) },
        popEnterTransition = { libraryReturnFrom(initialState.destination.route) },
    ) {
        val pureBlackEnabled by rememberPreference(PureBlackKey, defaultValue = false)
        val useDarkTheme = ForceDarkTheme
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            LibraryHomeScreen(
                navController = navController,
                pureBlack = remember(pureBlackEnabled, useDarkTheme) { pureBlackEnabled && useDarkTheme },
            )
        }
    }

    coverSourceComposable("library/songs") {
        LibrarySongsScreen(navController = navController)
    }

    composable(
        route = "library/playlists",
        exitTransition = { libraryExitTo(targetState.destination.route) },
        popEnterTransition = { libraryReturnFrom(initialState.destination.route) },
    ) {
        // the scope a playlist's cover needs to grow into its page, as on Home
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            LibraryPlaylistsScreen(navController = navController)
        }
    }

    coverSourceComposable("library/added") {
        LibraryRecentlyAddedScreen(navController = navController)
    }

    // Local: downloads and imported files, and the same pages again counting only those
    coverSourceComposable("library/local") {
        LibraryLocalScreen(navController = navController)
    }

    coverSourceComposable("library/local/added") {
        LibraryRecentlyAddedScreen(navController = navController, localOnly = true)
    }

    coverSourceComposable("library/local/albums") {
        LibraryAlbumsScreen(navController = navController, localOnly = true)
    }

    coverSourceComposable("library/local/artists") {
        LibraryArtistsScreen(navController = navController, localOnly = true)
    }

    composable(
        route = "library/local/playlists",
        exitTransition = { libraryExitTo(targetState.destination.route) },
        popEnterTransition = { libraryReturnFrom(initialState.destination.route) },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            LibraryPlaylistsScreen(navController = navController, localOnly = true)
        }
    }

    // Everything one service brought in, whatever playlist it went into: Imported from Spotify.
    coverSourceComposable(
        route = "library/imported/{source}",
        arguments = listOf(navArgument("source") { type = NavType.StringType }),
    ) {
        LibraryImportedScreen(navController = navController)
    }

    composable(Screens.Settings.route) {
        ProfileScreen(navController = navController)
    }

    composable("stats") {
        StatsScreen(navController = navController)
    }

    composable("cipher_settings") {
        CipherSettingsScreen(navController = navController)
    }

    composable("playback_diagnostics") {
        PlaybackDiagnosticsScreen(navController = navController)
    }

    composable("blossomtune_settings") {
        BlossomTuneSettingsScreen(navController = navController)
    }

    composable("general_settings") {
        GeneralSettingsScreen(navController = navController)
    }

    composable("audio_settings") {
        AudioSettingsScreen(navController = navController)
    }


    composable("appearance_settings") {
        AppearanceSettingsScreen(navController = navController)
    }

    composable("playback_settings") {
        PlaybackSettingsScreen(navController = navController)
    }

    composable("crossmix_settings") {
        CrossmixSettingsScreen(navController = navController)
    }

    composable("developer_space") {
        DeveloperSpaceScreen(navController = navController)
    }

    composable("experimental_settings") {
        ExperimentalSettingsScreen(navController = navController)
    }

    composable(ImportSyncRoute) {
        ImportSyncScreen(navController = navController)
    }

    composable(ImportProvidersRoute) {
        ImportProvidersScreen(navController = navController)
    }

    composable(ImportTuneMyMusicRoute) {
        TuneMyMusicImportScreen(navController = navController)
    }

    composable(ImportProgressRoute) {
        ImportProgressScreen(navController = navController)
    }

    composable(YouTubeSyncRoute) {
        YouTubeSyncScreen(navController = navController)
    }

    composable(
        route = AccountImportRoutePattern,
        arguments = listOf(navArgument("service") { type = NavType.StringType }),
    ) {
        AccountImportScreen(navController = navController)
    }

    composable(YouTubeLoginRoute) {
        YouTubeLoginScreen(navController = navController)
    }

    composable("equalizer") {
        EqualizerScreen(navController = navController)
    }

    composable("audio_lab") {
        AudioLabScreen(navController = navController)
    }

    composable("audio_fx") {
        AudioFxScreen(navController = navController)
    }

    composable("audio_quality") {
        AudioQualityScreen(navController = navController)
    }

    composable(ModulesRoute) {
        ModulesScreen(navController = navController)
    }

    composable(
        route = ModuleRoutePattern,
        arguments = listOf(navArgument("id") { type = NavType.StringType }),
    ) { entry ->
        ModuleScreen(
            navController = navController,
            id = entry.arguments?.getString("id").orEmpty(),
        )
    }

    composable(
        route = ModuleSignInRoutePattern,
        arguments = listOf(navArgument("id") { type = NavType.StringType }),
    ) { entry ->
        ModuleSignInScreen(
            navController = navController,
            id = entry.arguments?.getString("id").orEmpty(),
        )
    }

    composable(
        route = "browse/{browseId}",
        arguments = listOf(
            navArgument("browseId") {
                type = NavType.StringType
            }
        )
    ) {
        BrowseScreen(
            navController,
            scrollBehavior,
            it.arguments?.getString("browseId")
        )
    }

    composable(
        route = "search/{query}",
        arguments = listOf(
            navArgument("query") {
                type = NavType.StringType
            },
        ),
        enterTransition = {
            fadeIn(tween(250))
        },
        exitTransition = {
            when {
                targetState.destination.route.opensFromCover() -> libraryExitTo(targetState.destination.route)
                targetState.destination.route?.startsWith("search/") == true -> fadeOut(tween(200))
                else -> fadeOut(tween(200)) + slideOutHorizontally { -it / 2 }
            }
        },
        popEnterTransition = {
            when {
                initialState.destination.route.opensFromCover() -> coverReturnFrom(initialState.destination.route)
                initialState.destination.route?.startsWith("search/") == true -> fadeIn(tween(250))
                else -> fadeIn(tween(250)) + slideInHorizontally { -it / 2 }
            }
        },
        popExitTransition = {
            fadeOut(tween(200))
        },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            OnlineSearchResult(navController)
        }
    }

    composable(
        route = "album/{albumId}",
        arguments = listOf(
            navArgument("albumId") {
                type = NavType.StringType
            },
        ),

        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { libraryExitTo(targetState.destination.route) ?: fadeOut(tween(200)) },
        popEnterTransition = { coverReturnFrom(initialState.destination.route) ?: fadeIn(tween(300)) },

        popExitTransition = {
            scaleOut(
                animationSpec = tween(260),
                targetScale = 0.82f,
                transformOrigin = TransformOrigin(0.5f, 0.22f),
            ) + fadeOut(tween(180))
        },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            AlbumScreen(navController, scrollBehavior)
        }
    }

    coverSourceComposable(
        route = "artist/{artistId}",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
        ),
    ) {
        ArtistScreen(navController, scrollBehavior)
    }

    coverSourceComposable(
        route = "artist/{artistId}/songs",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
        ),
    ) {
        ArtistSongsScreen(navController, scrollBehavior)
    }

    coverSourceComposable(
        route = "artist/{artistId}/albums",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            }
        )
    ) {
        ArtistAlbumsScreen(navController, scrollBehavior)
    }

    // "similar to <artist>": the artists, playlists and songs around one artist, from its page
    coverSourceComposable(
        route = "artist/{artistId}/similar",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
        ),
    ) {
        ArtistSimilarScreen(navController)
    }

    coverSourceComposable(
        route = "artist/{artistId}/items?browseId={browseId}?params={params}",
        arguments = listOf(
            navArgument("artistId") {
                type = NavType.StringType
            },
            navArgument("browseId") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("params") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        ArtistItemsScreen(navController, scrollBehavior)
    }

    composable(
        route = "online_playlist/{playlistId}",
        arguments = listOf(
            navArgument("playlistId") {
                type = NavType.StringType
            },
        ),
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { libraryExitTo(targetState.destination.route) ?: fadeOut(tween(200)) },
        popEnterTransition = { coverReturnFrom(initialState.destination.route) ?: fadeIn(tween(300)) },

        popExitTransition = {
            scaleOut(
                animationSpec = tween(260),
                targetScale = 0.82f,
                transformOrigin = TransformOrigin(0.5f, 0.22f),
            ) + fadeOut(tween(180))
        },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            OnlinePlaylistScreen(navController, scrollBehavior)
        }
    }

    composable(
        route = "local_playlist/{playlistId}",
        arguments = listOf(
            navArgument("playlistId") {
                type = NavType.StringType
            },
        ),
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { libraryExitTo(targetState.destination.route) ?: fadeOut(tween(200)) },
        popEnterTransition = { coverReturnFrom(initialState.destination.route) ?: fadeIn(tween(300)) },

        popExitTransition = {
            scaleOut(
                animationSpec = tween(260),
                targetScale = 0.82f,
                transformOrigin = TransformOrigin(0.5f, 0.22f),
            ) + fadeOut(tween(180))
        },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            LocalPlaylistScreen(navController, scrollBehavior)
        }
    }

    composable(
        route = "auto_playlist/{playlist}",
        arguments = listOf(
            navArgument("playlist") {
                type = NavType.StringType
            },
        ),
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { libraryExitTo(targetState.destination.route) ?: fadeOut(tween(200)) },
        popEnterTransition = { coverReturnFrom(initialState.destination.route) ?: fadeIn(tween(300)) },

        popExitTransition = {
            scaleOut(
                animationSpec = tween(260),
                targetScale = 0.82f,
                transformOrigin = TransformOrigin(0.5f, 0.22f),
            ) + fadeOut(tween(180))
        },
    ) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
            AutoPlaylistScreen(navController, scrollBehavior)
        }
    }

    composable(
        route = "cache_playlist/{playlist}",
        arguments = listOf(
            navArgument("playlist") {
                type = NavType.StringType
            },
        ),
    ) {
        CachePlaylistScreen(navController, scrollBehavior)
    }

    composable(
        route = "top_playlist/{top}",
        arguments = listOf(
            navArgument("top") {
                type = NavType.StringType
            },
        ),
    ) {
        TopPlaylistScreen(navController, scrollBehavior)
    }

    coverSourceComposable(
        route = "genre/{browseId}?params={params}",
        arguments = listOf(
            navArgument("browseId") {
                type = NavType.StringType
            },
            navArgument("params") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        val pureBlackEnabled by rememberPreference(PureBlackKey, defaultValue = false)
        val useDarkTheme = ForceDarkTheme
        GenreScreen(
            navController = navController,
            pureBlack = remember(pureBlackEnabled, useDarkTheme) {
                pureBlackEnabled && useDarkTheme
            },
        )
    }

    composable(
        route = "youtube_browse/{browseId}?params={params}",
        arguments = listOf(
            navArgument("browseId") {
                type = NavType.StringType
                nullable = true
            },
            navArgument("params") {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        YouTubeBrowseScreen(navController)
    }

    coverSourceComposable("library/albums") {
        LibraryAlbumsScreen(navController = navController)
    }

    coverSourceComposable("library/artists") {
        LibraryArtistsScreen(navController = navController)
    }
}

internal data class LocalAudioMetadata(
    val title: String,
    val artist: String,
    val album: String?,
    val durationSeconds: Int,
    val mimeType: String?,
    val bitrate: Int?,
    val sampleRate: Int?,
    val embeddedArtwork: ByteArray?,
)

internal fun extractAudioMetadata(
    context: android.content.Context,
    uri: Uri,
): LocalAudioMetadata {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            ?: uri.lastPathSegment?.substringBeforeLast('.') ?: "Unknown"
        val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            ?: retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
            ?: "Unknown Artist"
        val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
        val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.toLongOrNull() ?: 0L
        val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
            ?: context.contentResolver.getType(uri)
        val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)
            ?.toIntOrNull()
        val sampleRate = try {

            if (android.os.Build.VERSION.SDK_INT >= 31) {
                retriever.extractMetadata(38)?.toIntOrNull()
            } else null
        } catch (_: Exception) { null }
        val embeddedArtwork = try { retriever.embeddedPicture } catch (_: Exception) { null }
        LocalAudioMetadata(
            title = title,
            artist = artist,
            album = album,
            durationSeconds = (durationMs / 1000).toInt(),
            mimeType = mimeType,
            bitrate = bitrate,
            sampleRate = sampleRate,
            embeddedArtwork = embeddedArtwork,
        )
    } catch (e: Exception) {

        LocalAudioMetadata(
            title = uri.lastPathSegment?.substringBeforeLast('.') ?: "Unknown",
            artist = "Unknown Artist",
            album = null,
            durationSeconds = 0,
            mimeType = context.contentResolver.getType(uri),
            bitrate = null,
            sampleRate = null,
            embeddedArtwork = null,
        )
    } finally {
        retriever.release()
    }
}
