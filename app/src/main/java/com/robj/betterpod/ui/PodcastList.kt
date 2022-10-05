package com.robj.betterpod.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.robj.betterpod.MainActivityPresenter
import com.robj.betterpod.networking.models.Podcast
import org.koin.androidx.compose.getViewModel


@Composable
fun PodcastListView(onNavigateToDetails: (podcast: Podcast) -> Unit) {
    val mainViewModel: MainActivityPresenter = getViewModel()
    when (val state = mainViewModel.state.value) {
        is MainActivityPresenter.State.Data -> {
            state.podcasts.takeIf { it.size > 4 }?.let { podcasts ->
                val state = rememberLazyListState()
                podcastList(
                    podcasts = podcasts.subList(
                        3,
                        8.coerceAtMost(podcasts.size)
                    ), onNavigateToDetails = onNavigateToDetails,
                    headerView = {
                        podcastPager(
                            podcasts.subList(0, 4.coerceAtMost(podcasts.size)),
                            onNavigateToDetails
                        )
                    },
                    state = state
                )
            }
        }
        MainActivityPresenter.State.Empty -> empty()
        is MainActivityPresenter.State.Error -> error(state.errorMsg)
        MainActivityPresenter.State.Loading -> loading()
    }
}