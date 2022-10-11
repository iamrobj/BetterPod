package com.robj.betterpod.ui.compose.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robj.betterpod.DispatcherProvider
import com.robj.betterpod.database.models.PodcastViewModel
import com.robj.betterpod.networking.ApiRepo
import com.robj.betterpod.networking.DbRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val apiRepo: ApiRepo,
    private val dbRepo: DbRepo,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private var searchJob: Job? = null
    val state: MutableState<State> = mutableStateOf(State.Idle)

    fun search(query: String) {
        try {
            searchJob?.cancel()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        query.takeIf { it.isNotBlank() }?.apply {
            searchJob = viewModelScope.launch(context = dispatcherProvider.io) {
                state.value = State.Loading
                delay(500)
                apiRepo.searchPodcasts(query)
                    .map { podcasts ->
                        dbRepo.mapToSubscribedPodcasts(podcasts)
                    }
                    .onSuccess { podcasts ->
                        state.value = if (podcasts.isEmpty()) {
                            State.Empty
                        } else {
                            State.Data(podcasts)
                        }
                    }.onFailure {
                        it.printStackTrace()
                        state.value = State.Error("Something went wrong")
                    }
            }
        } ?: resetSearch()
    }

    fun resetSearch() {
        state.value = State.Idle
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        object Idle : State()
        data class Error(val errorMsg: String) : State()
        data class Data(val podcasts: List<PodcastViewModel>) : State()
    }


}