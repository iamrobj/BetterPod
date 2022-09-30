package com.robj.betterpod.networking

import com.robj.betterpod.networking.models.Podcast

class ApiRepo(
    private val dbRepo: DbRepo,
    private val apiService: ApiService
) {

    suspend fun getTrendingPodcasts(): Result<List<Podcast>> = runCatching {
        apiService.getTrendingPodcasts().feeds.apply {
            dbRepo.addAll(this)
        }
    }
}
