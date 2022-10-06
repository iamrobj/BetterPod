package com.robj.betterpod.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robj.betterpod.R
import com.robj.betterpod.ui.compose.navigation.AppBarState
import com.robj.betterpod.ui.compose.navigation.SetupNavGraph
import com.robj.betterpod.ui.theme.BetterPodTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var appBarState by remember {
                mutableStateOf(
                    AppBarState(title = getString(R.string.title_discover))
                )
            }
            BetterPodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = appBarState.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                navigationIcon = appBarState.navigationIcon,
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                                elevation = 10.dp
                            )
                        },
                        bottomBar = {
                            BottomAppBar { /* Bottom app bar content */ }
                        }
                    ) {
                        SetupNavGraph() { newAppBarState ->
                            appBarState = newAppBarState
                        }
                    }
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