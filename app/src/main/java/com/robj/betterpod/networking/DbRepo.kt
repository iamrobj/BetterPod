package com.robj.betterpod.networking

import com.robj.betterpod.database.AppDatabase
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepo(
    private val appDatabase: AppDatabase
) {

    suspend fun getPodcast(id: Int): Result<Podcast> = runCatching {
        withContext(Dispatchers.IO) {
            appDatabase.podcastDao().findById(id)
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
}
