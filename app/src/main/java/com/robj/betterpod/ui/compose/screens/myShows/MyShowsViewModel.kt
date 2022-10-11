package com.robj.betterpod.ui.compose.screens.myShows

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robj.betterpod.DispatcherProvider
import com.robj.betterpod.database.models.PodcastViewModel
import com.robj.betterpod.networking.DbRepo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyShowsViewModel(
    private val dbRepo: DbRepo,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val state: MutableState<State> = mutableStateOf(State.Loading)

    init {
        viewModelScope.launch(context = dispatcherProvider.io) {
            dbRepo.getAllSubscribedPodcasts()
                .onSuccess { podcasts ->
                    withContext(context = dispatcherProvider.main) {
                        state.value = if (podcasts.isEmpty()) {
                            State.Empty
                        } else {
                            State.Data(podcasts)
                        }
                    }
                }.onFailure {
                    it.printStackTrace()
                    withContext(context = dispatcherProvider.main) {
                        state.value = State.Error("Something went wrong")
                    }
                }
        }
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Error(val errorMsg: String) : State()
        data class Data(val podcasts: List<PodcastViewModel>) : State()
    }

}