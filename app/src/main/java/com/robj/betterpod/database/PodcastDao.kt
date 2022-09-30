package com.robj.betterpod.database

import androidx.room.*
import com.robj.betterpod.networking.models.Podcast

@Dao
interface PodcastDao {
    @Query("SELECT * FROM podcast")
    fun getAll(): List<Podcast>

    @Query("SELECT * FROM podcast WHERE id IN (:podcastIds)")
    fun loadAllByIds(podcastIds: IntArray): List<Podcast>

    @Query("SELECT * FROM podcast WHERE id is :id LIMIT 1")
    fun findById(id: Int): Podcast

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg podcasts: Podcast)

    @Delete
    fun delete(podcast: Podcast)
}