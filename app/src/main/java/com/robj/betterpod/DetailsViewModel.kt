package com.robj.betterpod

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.robj.betterpod.networking.DbRepo
import com.robj.betterpod.networking.models.Podcast

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val dbRepo: DbRepo,
) : ViewModel() {

    private val podcastId: Int = checkNotNull(savedStateHandle["id"])

    val state: MutableState<State> = mutableStateOf(State.Loading)

    suspend fun loadPodcast() {
        dbRepo.getPodcast(podcastId)
            .onSuccess { podcast ->
                state.value = State.Data(podcast)
            }.onFailure {
                it.printStackTrace()
                state.value = State.Error("Something went wrong")
            }
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Error(val errorMsg: String) : State()
        data class Data(val podcast: Podcast) : State()
    }


}