package com.robj.betterpod.database

import androidx.room.*
import com.robj.betterpod.networking.models.Episode

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episode")
    fun getAll(): List<Episode>

    @Query("SELECT * FROM episode WHERE id IN (:episodeIds)")
    fun loadAllByIds(episodeIds: IntArray): List<Episode>

    @Query("SELECT * FROM episode WHERE id is :id LIMIT 1")
    fun findById(id: Int): Episode

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg episodes: Episode)

    @Delete
    fun delete(episode: Episode)
}