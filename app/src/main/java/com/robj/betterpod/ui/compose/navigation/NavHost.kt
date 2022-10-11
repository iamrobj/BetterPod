package com.robj.betterpod.ui.compose.navigation

import android.content.res.Resources
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.robj.betterpod.R
import com.robj.betterpod.ui.compose.navigation.Animations.slideDownExitAnim
import com.robj.betterpod.ui.compose.navigation.Animations.slideUpEnterAnim
import com.robj.betterpod.ui.compose.screens.details.PodcastDetails
import com.robj.betterpod.ui.compose.screens.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    AnimatedNavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen() { podcast ->
                navController.navigate(
                    route = Screen.Details.route.replace(
                        "{id}",
                        podcast.id.toString()
                    ).replace("{title}", podcast.title)
                )
            }
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }),
            enterTransition = { slideUpEnterAnim() },
            exitTransition = { slideDownExitAnim() },
        ) {
            PodcastDetails()
        }
        composable(
            route = Screen.MyShows.route,
        ) {
            //TODO
        }
        composable(
            route = Screen.Downloads.route,
        ) {
            //TODO
        }
        composable(
            route = Screen.Settings.route,
        ) {
            //TODO
        }
    }
}

fun NavHostController.navigateToDiscover() {
    navigate(route = Screen.Home.route)
}

fun NavHostController.navigateToMyShows() {
    navigate(route = Screen.MyShows.route)
}

fun NavHostController.navigateToDownloads() {
    navigate(route = Screen.Downloads.route)
}

fun NavHostController.navigateToSettings() {
    navigate(route = Screen.Settings.route)
}

fun getTitleByRoute(
    resources: Resources,
    route: String?,
    arguments: Bundle?
): String {
    return route?.let {
        when (Screen.getByRoute(it)) {
            Screen.Home -> resources.getString(R.string.title_discover)
            Screen.MyShows -> resources.getString(R.string.title_my_shows)
            Screen.Downloads -> resources.getString(R.string.title_downloads)
            Screen.Settings -> resources.getString(R.string.title_settings)
            Screen.Details -> arguments?.getString("title") ?: ""
        }
    } ?: ""
}

fun showBackIcon(route: String?): Boolean {
    return route?.let {
        when (Screen.getByRoute(it)) {
            Screen.Details -> true
            else -> false
        }
    } ?: false
}