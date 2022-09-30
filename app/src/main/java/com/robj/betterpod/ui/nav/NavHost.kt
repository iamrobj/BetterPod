package com.robj.betterpod.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.robj.betterpod.ui.PodcastDetails
import com.robj.betterpod.ui.PodcastListView

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            PodcastListView { podcast ->
                navController.navigate(route = Screen.Details.route + "/${podcast.id}")
            }
        }
        composable(
            route = Screen.Details.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            PodcastDetails { podcast -> }
        }
    }
}