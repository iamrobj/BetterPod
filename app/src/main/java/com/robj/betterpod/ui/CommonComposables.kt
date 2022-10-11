package com.robj.betterpod.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.robj.betterpod.R
import com.robj.betterpod.database.models.PodcastViewModel
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.compose.navigation.navigateToDiscover
import com.robj.betterpod.ui.compose.navigation.navigateToDownloads
import com.robj.betterpod.ui.compose.navigation.navigateToMyShows
import com.robj.betterpod.ui.compose.navigation.navigateToSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun podcastRow(
    podcastViewModel: PodcastViewModel,
    onNavigateToDetails: (podcast: Podcast) -> Unit
) {
    val podcast = podcastViewModel.podcast
    val subscriptionState = rememberSaveable { mutableStateOf(podcastViewModel.isSubscribed) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onNavigateToDetails(podcast)
                },
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.artwork)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(48.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = podcast.title,
                style = TextStyle(fontSize = 16.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = podcast.title,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = {
            subscriptionState.value = !subscriptionState.value
            GlobalScope.launch(Dispatchers.IO) { //TODO: Bad practive
                podcastViewModel.onSubscriptionChanged(subscriptionState.value)
            }
        }) {
            Icon(
                imageVector = if (subscriptionState.value) {
                    Icons.Outlined.CheckCircle
                } else {
                    Icons.Outlined.AddCircle
                },
                contentDescription = null
            )
        }
    }
}

@Composable
fun empty() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Empty")
    }
}


@Composable
fun errorState(msg: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = msg)
    }
}

@Composable
fun loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun UpIcon(navController: NavController) {
    IconButton(onClick = {
        navController.navigateUp()
    }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
fun EpisodeRow(episode: Episode, isMyFeed: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, top = 8.dp)
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(episode.image.takeIf { it.isNotBlank() } ?: episode.feedImage)
//                .crossfade(true)
//                .build(),
//            placeholder = painterResource(R.drawable.ic_launcher_background),
//            contentDescription = stringResource(R.string.app_name),
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(72.dp)
//                .padding(end = 16.dp)
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(4.dp))
//        )
        Column() {
            Text(
                if (!isMyFeed) {
                    episode.getEpisodeNumber(LocalContext.current.resources) + " • " + episode.getFormattedDate(
                        LocalContext.current.resources
                    )
                } else {
                    episode.getFormattedDate(
                        LocalContext.current.resources
                    )
                },
                style = TextStyle(fontSize = 12.sp)
            )
            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = episode.title,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(episode.duration.toFormattedTime(), style = TextStyle(fontSize = 12.sp))
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(
                        id = R.string.title_discover
                    )
                )
            },
            alwaysShowLabel = false,
            label = { Text(text = stringResource(id = R.string.title_discover)) },
            selected = true, //TODO
            onClick = {
                navController.navigateToDiscover()
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = stringResource(
                        id = R.string.title_my_shows
                    )
                )
            },
            label = { Text(text = stringResource(id = R.string.title_my_shows)) },
            selected = true, //TODO
            onClick = {
                navController.navigateToMyShows()
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(
                        id = R.string.title_downloads
                    )
                )
            },
            label = { Text(text = stringResource(id = R.string.title_downloads)) },
            selected = true, //TODO
            onClick = {
                navController.navigateToDownloads()
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(
                        id = R.string.title_settings
                    )
                )
            },
            label = { Text(text = stringResource(id = R.string.title_settings)) },
            selected = true, //TODO
            onClick = {
                navController.navigateToSettings()
            }
        )
    }
}