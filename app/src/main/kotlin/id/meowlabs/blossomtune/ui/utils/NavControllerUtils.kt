// NavControllerUtils.kt

package id.meowlabs.blossomtune.ui.utils

import androidx.navigation.NavController
import id.meowlabs.blossomtune.ui.screens.Screens

fun NavController.backToMain() {
    val mainRoutes = Screens.MainScreens.map { it.route }

    while (previousBackStackEntry != null &&
        currentBackStackEntry?.destination?.route !in mainRoutes
    ) {
        popBackStack()
    }
}
