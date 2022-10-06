package com.robj.betterpod.ui.compose.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.robj.betterpod.R
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.empty
import com.robj.betterpod.ui.loading
import com.robj.betterpod.ui.theme.TitleTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@Composable
fun PodcastDetails() {
    val detailsViewModel: DetailViewModel = getViewModel()
    rememberCoroutineScope().launch {
        detailsViewModel.loadPodcast()
        detailsViewModel.loadEpisodes()
    }
    Column(modifier = Modifier.background(Color.White)) {
        when (val state = detailsViewModel.podcastState.value) {
            is DetailViewModel.PodcastState.Data -> PodcastDetailHeader(podcast = state.podcast)
            DetailViewModel.PodcastState.Empty -> empty()
            is DetailViewModel.PodcastState.Error -> com.robj.betterpod.ui.error(state.errorMsg)
            DetailViewModel.PodcastState.Loading -> loading()
        }
        when (val state = detailsViewModel.episodeState.value) {
            is DetailViewModel.EpisodeState.Data -> LazyColumn(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    top = 0.dp
                )
            ) {
                items(state.episodes) { episode ->
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(episode.image.takeIf { it.isNotBlank() } ?: episode.feedImage)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_launcher_background),
                            contentDescription = stringResource(R.string.app_name),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(60.dp)
                                .padding(end = 16.dp)
                                .aspectRatio(1f)
                        )
                        Column() {
                            Text(episode.datePublishedPretty, style = TextStyle(fontSize = 12.sp))
                            Text(episode.title, style = TextStyle(fontSize = 18.sp))
                            Text(episode.duration.toString(), style = TextStyle(fontSize = 12.sp))
                        }
                    }
                }
            }
            DetailViewModel.EpisodeState.Empty -> empty()
            is DetailViewModel.EpisodeState.Error -> com.robj.betterpod.ui.error(state.errorMsg)
            DetailViewModel.EpisodeState.Loading -> loading()
        }
    }
}

@Composable
fun PodcastDetailHeader(podcast: Podcast) {
    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.artwork)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(.45f)
                .aspectRatio(1f)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = podcast.title, style = TitleTheme())
            Text(text = podcast.description, maxLines = 5, overflow = TextOverflow.Ellipsis)
            Text(
                text = podcast.title,
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
        }
    }
}
