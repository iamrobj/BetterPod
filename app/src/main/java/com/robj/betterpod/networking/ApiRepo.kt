package com.robj.betterpod.networking

import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast

class ApiRepo(
    private val dbRepo: DbRepo,
    private val apiService: ApiService
) {

    suspend fun getTrendingPodcasts(): Result<List<Podcast>> = runCatching {
        apiService.getTrendingPodcasts().feeds.apply {
            dbRepo.addAllPodcasts(this)
        }
    }

    suspend fun searchPodcastsByName(query: String): Result<List<Podcast>> = runCatching {
        apiService.searchPodcastsByName(query).feeds.apply {
            dbRepo.addAllPodcasts(this)
        }
    }

    suspend fun searchPodcasts(query: String): Result<List<Podcast>> = runCatching {
        apiService.searchPodcasts(query).feeds.apply {
            dbRepo.addAllPodcasts(this)
        }
    }

    suspend fun getPodcastEpisodes(podcastId: Int): Result<List<Episode>> = runCatching {
        apiService.findEpisodes(podcastId).items.apply {
            dbRepo.addAllEpisodes(this)
        }
    }
}
