package com.robj.betterpod.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.robj.betterpod.R
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast

@Composable
fun podcastRow(podcast: Podcast, onNavigateToDetails: (podcast: Podcast) -> Unit) {
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
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
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