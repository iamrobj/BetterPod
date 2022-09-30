package com.robj.betterpod.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.robj.betterpod.R
import com.robj.betterpod.networking.models.Podcast

@Composable
fun podcastList(podcasts: List<Podcast>, onNavigateToDetails: (podcast: Podcast) -> Unit) {
    LazyColumn {
        items(podcasts) { podcast ->
            podcastRow(podcast = podcast, onNavigateToDetails)
        }
    }
}

@Composable
fun podcastRow(podcast: Podcast, onNavigateToDetails: (podcast: Podcast) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    onNavigateToDetails(podcast)
                },
            )
            .background(MaterialTheme.colors.secondary),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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
                    .width(48.dp)
                    .clip(CircleShape)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                text = podcast.title,
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
fun error(msg: String) {
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