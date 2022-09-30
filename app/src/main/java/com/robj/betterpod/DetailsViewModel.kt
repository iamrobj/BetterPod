package com.robj.betterpod

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.robj.betterpod.networking.ApiRepo
import com.robj.betterpod.networking.DbRepo
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast

class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val dbRepo: DbRepo,
    private val apiRepo: ApiRepo
) : ViewModel() {

    private val podcastId: Int = checkNotNull(savedStateHandle["id"])

    val podcastState: MutableState<PodcastState> = mutableStateOf(PodcastState.Loading)
    val episodeState: MutableState<EpisodeState> = mutableStateOf(EpisodeState.Loading)

    suspend fun loadPodcast() {
        dbRepo.getPodcast(podcastId)
            .onSuccess { podcast ->
                podcastState.value = PodcastState.Data(podcast)
            }.onFailure {
                it.printStackTrace()
                podcastState.value = PodcastState.Error("Something went wrong")
            }
    }

    suspend fun loadEpisodes() {
        apiRepo.getPodcastEpisodes(podcastId)
            .onSuccess { episodes ->
                episodeState.value = EpisodeState.Data(episodes)
            }.onFailure {
                it.printStackTrace()
                episodeState.value = EpisodeState.Error("Unable to load episodes")
            }
    }

    sealed class PodcastState {
        object Loading : PodcastState()
        object Empty : PodcastState()
        data class Error(val errorMsg: String) : PodcastState()
        data class Data(val podcast: Podcast) : PodcastState()
    }

    sealed class EpisodeState {
        object Loading : EpisodeState()
        object Empty : EpisodeState()
        data class Error(val errorMsg: String) : EpisodeState()
        data class Data(val episodes: List<Episode>) : EpisodeState()
    }


}