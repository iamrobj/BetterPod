package com.robj.betterpod.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.robj.betterpod.DetailsViewModel
import com.robj.betterpod.networking.models.Podcast
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@Composable
fun PodcastDetails(onNavigateToDetails: (podcast: Podcast) -> Unit) {
    val detailsViewModel: DetailsViewModel = getViewModel()
    rememberCoroutineScope().launch {
        detailsViewModel.loadPodcast()
    }
    when (val state = detailsViewModel.state.value) {
        is DetailsViewModel.State.Data -> error(msg = state.podcast.title)
        DetailsViewModel.State.Empty -> empty()
        is DetailsViewModel.State.Error -> error(state.errorMsg)
        DetailsViewModel.State.Loading -> loading()
    }
}