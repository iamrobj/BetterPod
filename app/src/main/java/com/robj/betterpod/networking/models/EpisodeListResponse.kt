package com.robj.betterpod.networking.models

@kotlinx.serialization.Serializable
data class EpisodeListResponse(

    val status: String,
    val items: List<Episode>,
    val count: Int,
    val query: Int,
    val description: String
)