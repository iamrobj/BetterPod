package com.robj.betterpod.ui.compose.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Podcast
import com.robj.betterpod.ui.*
import com.robj.betterpod.ui.compose.screens.search.SearchScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(onNavigateToDetails: (podcast: Podcast) -> Unit) {
    val mainViewModel: HomeViewModel = getViewModel()
    Column {
        SearchScreen(
            onNavigateToDetails
        )
        loadCategoryChips(homeViewModel = mainViewModel) {

        }
        loadData(mainViewModel, onNavigateToDetails)
    }
}

@Composable
fun loadCategoryChips(
    homeViewModel: HomeViewModel,
    onCategorySelected: (category: Category) -> Unit
) {
    when (val state = homeViewModel.categoryState.value) {
        is HomeViewModel.CategoryState.Data -> CategoryChips(state.categories) { categories ->
            homeViewModel.loadByCategory(categories)
        }
        is HomeViewModel.CategoryState.Error -> errorState(state.errorMsg)
        HomeViewModel.CategoryState.Loading -> loading()
    }
}

@Composable
fun loadData(homeViewModel: HomeViewModel, onNavigateToDetails: (podcast: Podcast) -> Unit) {
    when (val state = homeViewModel.state.value) {
        is HomeViewModel.State.Data -> {
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
        HomeViewModel.State.Empty -> empty()
        is HomeViewModel.State.Error -> errorState(state.errorMsg)
        HomeViewModel.State.Loading -> loading()
    }
}
