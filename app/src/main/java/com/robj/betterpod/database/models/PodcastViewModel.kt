package com.robj.betterpod.database.models

import com.robj.betterpod.networking.models.Podcast

data class PodcastViewModel(
    val podcast: Podcast,
    var isSubscribed: Boolean,
) {
    lateinit var onSubscriptionChanged: suspend (isSubscribed: Boolean) -> Unit
}