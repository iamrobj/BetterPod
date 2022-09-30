package com.robj.betterpod.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast

@Database(entities = [Podcast::class, Episode::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
    abstract fun episodeDao(): EpisodeDao
}