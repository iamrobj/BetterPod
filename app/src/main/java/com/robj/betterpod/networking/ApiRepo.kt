package com.robj.betterpod.networking

import com.robj.betterpod.networking.models.Category
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

    suspend fun getAllCategories(): Result<List<Category>> = runCatching {
        apiService.getAllCategories().feeds.apply {
            dbRepo.addAllCategories(this)
        }.sortedBy { it.name }
    }

    suspend fun getPodcastsByCategory(category: Category): Result<List<Podcast>> = runCatching {
        apiService.trendingByCategory(category.id.toString()).feeds.apply {
            dbRepo.addAllPodcasts(this)
        }
    }

    suspend fun getPodcastsByCategories(categories: List<Category>): Result<List<Podcast>> =
        runCatching {
            apiService.trendingByCategory(categories.joinToString(separator = ",") { it.id.toString() }).feeds.apply {
                dbRepo.addAllPodcasts(this)
            }
        }
}
