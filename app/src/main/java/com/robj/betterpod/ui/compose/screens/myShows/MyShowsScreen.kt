package com.robj.betterpod.ui.compose.screens.myShows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.empty
import com.robj.betterpod.ui.errorState
import com.robj.betterpod.ui.loading
import com.robj.betterpod.ui.podcastList
import org.koin.androidx.compose.getViewModel


@Composable
fun MyShowsScreen(onNavigateToDetails: (podcast: Podcast) -> Unit) {
    val myShowsViewModel: MyShowsViewModel = getViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        loadData(myShowsViewModel, onNavigateToDetails)
    }
}

@Composable
fun loadData(myShowsViewModel: MyShowsViewModel, onNavigateToDetails: (podcast: Podcast) -> Unit) {
    when (val state = myShowsViewModel.state.value) {
        is MyShowsViewModel.State.Data -> {
            podcastList(
                podcasts = state.podcasts, onNavigateToDetails = onNavigateToDetails,
                state = rememberLazyListState()
            )
        }
        MyShowsViewModel.State.Empty -> empty()
        is MyShowsViewModel.State.Error -> errorState(state.errorMsg)
        MyShowsViewModel.State.Loading -> loading()
    }
}
