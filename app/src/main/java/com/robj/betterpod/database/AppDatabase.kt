package com.robj.betterpod.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.robj.betterpod.networking.models.Category
import com.robj.betterpod.networking.models.Episode
import com.robj.betterpod.networking.models.Podcast

@Database(entities = [Podcast::class, Episode::class, Category::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun categoryDao(): CategoryDao
}