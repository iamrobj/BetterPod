package com.robj.betterpod.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class Trending(
    val status: String,
    val feeds: List<Podcast>,
    val count: Int,
    val max: String?,
    val since: Int,
    val description: String
)