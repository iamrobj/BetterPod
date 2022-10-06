package com.robj.betterpod.ui.compose.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.*
import org.koin.androidx.compose.getViewModel

class TextFieldState {
    var text: String by mutableStateOf("")
    var hasFocus: Boolean by mutableStateOf(false)
}


@Composable
fun SearchScreen(
    onNavigateToDetails: (podcast: Podcast) -> Unit
) {
    val searchViewModel: SearchViewModel = getViewModel()
    val textState = remember {
        TextFieldState()
    }
    BackHandler(enabled = searchViewModel.state.value != SearchViewModel.State.Idle) {
        textState.text = ""
        textState.hasFocus = false
        searchViewModel.resetSearch()
    }

    Column(Modifier.wrapContentHeight()) {
        SearchView(textState) { query ->
            searchViewModel.search(query)
        }
        when (val state = searchViewModel.state.value) {
            is SearchViewModel.State.Data -> {
                val lazyListState = rememberLazyListState()
                podcastList(
                    podcasts = state.podcasts,
                    onNavigateToDetails = onNavigateToDetails,
                    state = lazyListState
                )
            }
            SearchViewModel.State.Empty -> empty()
            is SearchViewModel.State.Error -> errorState(state.errorMsg)
            SearchViewModel.State.Loading -> loading()
            SearchViewModel.State.Idle -> textState.text = ""
        }
    }
}