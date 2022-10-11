package com.robj.betterpod.networking

import com.robj.betterpod.database.AppDatabase
import com.robj.betterpod.database.models.PodcastViewModel
import com.robj.betterpod.database.models.Subscription
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepo(
    private val appDatabase: AppDatabase
) {

    suspend fun getPodcast(id: Int): Result<PodcastViewModel> = runCatching {
        withContext(Dispatchers.IO) {
            buildPodcastViewModel(
                appDatabase.podcastDao().findById(id),
                appDatabase.subscriptionDao().findById(id) != null
            )
        }
    }

    suspend fun addAllPodcasts(podcasts: List<Podcast>) {
        withContext(Dispatchers.IO) {
            appDatabase.podcastDao().insertAll(*podcasts.toTypedArray())
        }
    }

    suspend fun addAllEpisodes(episodes: List<Episode>) {
        withContext(Dispatchers.IO) {
            appDatabase.episodeDao().insertAll(*episodes.toTypedArray())
        }
    }

    suspend fun addAllCategories(categories: List<Category>) {
        withContext(Dispatchers.IO) {
            appDatabase.categoryDao().insertAll(*categories.toTypedArray())
        }
    }

    suspend fun subscribeToPodcast(podcast: Podcast) {
        withContext(Dispatchers.IO) {
            appDatabase.subscriptionDao().insertAll(Subscription(podcast.id))
        }
    }

    suspend fun unsubscribeFromPodcast(podcast: Podcast) {
        withContext(Dispatchers.IO) {
            appDatabase.subscriptionDao().delete(Subscription(podcast.id))
        }
    }

    suspend fun getAllSubscribedPodcasts(): Result<List<PodcastViewModel>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                appDatabase.subscriptionDao().getAll().map { it.podcastId }.let { ids ->
                    appDatabase.podcastDao().getAllByIds(ids.toIntArray()).map { podcast ->
                        buildPodcastViewModel(
                            podcast,
                            true
                        )
                    }
                }
            }
        }
    }

    suspend fun mapToSubscribedPodcasts(podcasts: List<Podcast>): List<PodcastViewModel> {
        return withContext(Dispatchers.IO) {
            appDatabase.subscriptionDao().getAll().map { it.podcastId }.let { ids ->
                podcasts.map { podcast ->
                    buildPodcastViewModel(podcast, ids.contains(podcast.id))
                }
            }
        }
    }

    private fun buildPodcastViewModel(podcast: Podcast, isSubscribed: Boolean): PodcastViewModel {
        val podcastViewModel = PodcastViewModel(
            podcast,
            isSubscribed
        ).apply {
            onSubscriptionChanged = { subscription ->
                this.isSubscribed = subscription
                if (this.isSubscribed) {
                    subscribeToPodcast(this.podcast)
                } else {
                    unsubscribeFromPodcast(this.podcast)
                }
            }
        }
        return podcastViewModel
    }

}
