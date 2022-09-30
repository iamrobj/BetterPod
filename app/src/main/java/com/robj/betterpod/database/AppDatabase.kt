package com.robj.betterpod.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robj.betterpod.networking.models.Podcast

@Database(entities = [Podcast::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
}