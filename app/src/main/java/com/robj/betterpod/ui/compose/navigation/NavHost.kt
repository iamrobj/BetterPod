package com.robj.betterpod.ui.compose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.robj.betterpod.R
import com.robj.betterpod.ui.UpIcon
import com.robj.betterpod.ui.compose.navigation.Animations.slideDownExitAnim
import com.robj.betterpod.ui.compose.navigation.Animations.slideUpEnterAnim
import com.robj.betterpod.ui.compose.screens.details.PodcastDetails
import com.robj.betterpod.ui.compose.screens.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(onComposing: (appBarState: AppBarState) -> Unit) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = Screen.Home.route
        ) {
            onComposing(
                AppBarState(
                    title = stringResource(R.string.title_discover)
                )
            )
            HomeScreen() { podcast ->
                navController.navigate(route = Screen.Details.route + "/${podcast.id}/${podcast.title}")
            }
        }
        composable(
            route = Screen.Details.route + "/{id}/{title}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }),
            enterTransition = { slideUpEnterAnim() },
            exitTransition = { slideDownExitAnim() },
        ) {
            onComposing(
                AppBarState(
                    title = it.arguments?.getString("title") ?: ""
                ) {
                    UpIcon(navController)
                }
            )
            PodcastDetails()
        }
    }
}

data class AppBarState(
    var title: String,
    var navigationIcon: @Composable (() -> Unit)? = null
)