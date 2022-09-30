package com.robj.betterpod.networking

import com.robj.betterpod.networking.models.EpisodeListResponse
import com.robj.betterpod.networking.models.PodcastListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("podcasts/trending")
    suspend fun getTrendingPodcasts(): PodcastListResponse

    @GET("search/bytitle")
    suspend fun searchPodcastsByName(@Query("q") query: String): PodcastListResponse

    @GET("episodes/byfeedid")
    suspend fun findEpisodes(
        @Query("id") podcastId: Int,
        @Query("max") maxEpisodes: Int = 1000
    ): EpisodeListResponse

}
