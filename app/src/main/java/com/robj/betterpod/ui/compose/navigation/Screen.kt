package com.robj.betterpod.ui.compose.navigation

enum class Screen(val route: String) {

    Home(route = "home"),
    MyShows(route = "my_shows"),
    Downloads(route = "downloads"),
    Settings(route = "settings"),
    Details(route = "details/{id}/{title}");

    companion object {
        fun getByRoute(route: String): Screen {
            return Screen.values().first { it.route == route }
        }
    }

}