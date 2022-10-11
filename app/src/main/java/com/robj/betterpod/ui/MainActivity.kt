package com.robj.betterpod.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.robj.betterpod.ui.compose.navigation.SetupNavGraph
import com.robj.betterpod.ui.compose.navigation.getTitleByRoute
import com.robj.betterpod.ui.compose.navigation.showBackIcon
import com.robj.betterpod.ui.theme.BetterPodTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetterPodTheme {
                val res = LocalContext.current.resources
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    var actionBarTitle by rememberSaveable { mutableStateOf("") }
                    var showBackNavIcon by rememberSaveable { mutableStateOf(false) }

                    LaunchedEffect(navController) {
                        navController.currentBackStackEntryFlow.collect { backStackEntry ->
                            actionBarTitle = getTitleByRoute(
                                res,
                                backStackEntry.destination.route,
                                backStackEntry.arguments
                            )
                            showBackNavIcon = showBackIcon(backStackEntry.destination.route)
                        }
                    }
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = actionBarTitle,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = if (showBackNavIcon) {
                                    {
                                        UpIcon(navController = navController)
                                    }
                                } else {
                                    null
                                },
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                                elevation = 10.dp
                            )
                        },
                        content = {
                            SetupNavGraph(navController)
                        },
                        bottomBar = {
                            if (!showBackNavIcon) {
                                BottomNavBar(navController)
                            } else {
                                null
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BetterPodTheme {
        Greeting("Android")
    }
}