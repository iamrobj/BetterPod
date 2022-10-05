package com.robj.betterpod.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.robj.betterpod.MainActivityPresenter
import com.robj.betterpod.networking.models.Podcast
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@Composable
fun PodcastListView(onNavigateToDetails: (podcast: Podcast) -> Unit) {
    val mainViewModel: MainActivityPresenter = getViewModel()
    rememberCoroutineScope().launch {
        mainViewModel.loadTrendingPodcasts()
    }
    when (val state = mainViewModel.state.value) {
        is MainActivityPresenter.State.Data -> {
            state.podcasts.takeIf { it.size > 4 }?.let { podcasts ->
                podcastList(
                    podcasts = podcasts.subList(
                        3,
                        8.coerceAtMost(podcasts.size)
                    ), onNavigateToDetails = onNavigateToDetails,
                    headerView = {
                        podcastPager(
                            state.podcasts.subList(0, 4.coerceAtMost(state.podcasts.size)),
                            onNavigateToDetails
                        )
                    }
                )
            }
        }
        MainActivityPresenter.State.Empty -> empty()
        is MainActivityPresenter.State.Error -> error(state.errorMsg)
        MainActivityPresenter.State.Loading -> loading()
    }
}