package com.robj.betterpod.ui.compose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.robj.betterpod.ui.PodcastDetails
import com.robj.betterpod.ui.PodcastListView
import com.robj.betterpod.ui.compose.navigation.Animations.slideDownExitAnim
import com.robj.betterpod.ui.compose.navigation.Animations.slideUpEnterAnim

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            PodcastListView { podcast ->
                navController.navigate(route = Screen.Details.route + "/${podcast.id}")
            }
        }
        composable(
            route = Screen.Details.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),

            enterTransition = { slideUpEnterAnim() },
            exitTransition = { slideDownExitAnim() },
        ) {
            PodcastDetails()
        }
    }
}