package com.robj.betterpod.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class PodcastListResponse(
    val status: String,
    val feeds: List<Podcast>,
    val count: Int,
    val max: String? = null,
    val since: Int? = null,
    val description: String
)