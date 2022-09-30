package com.robj.betterpod.networking

import com.robj.betterpod.networking.models.Trending
import retrofit2.http.GET

interface ApiService {

    @GET("podcasts/trending?pretty")
    suspend fun getTrendingPodcasts(): Trending

}
