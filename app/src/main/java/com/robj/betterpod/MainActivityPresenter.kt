package com.robj.betterpod

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robj.betterpod.networking.ApiRepo
import com.robj.betterpod.networking.models.Podcast

class MainActivityPresenter(
    private val apiRepo: ApiRepo,
) : ViewModel() {

    val state: MutableState<State> = mutableStateOf(State.Loading)

    suspend fun loadTrendingPodcasts() {
        apiRepo.getTrendingPodcasts()
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

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Error(val errorMsg: String) : State()
        data class Data(val podcasts: List<Podcast>) : State()
    }


}