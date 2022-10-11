package com.robj.betterpod.database

import androidx.room.*
import com.robj.betterpod.database.models.Subscription

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscription")
    fun getAll(): List<Subscription>

    @Query("SELECT * FROM subscription WHERE podcastId IN (:subscriptionIds)")
    fun loadAllByIds(subscriptionIds: IntArray): List<Subscription>

    @Query("SELECT * FROM subscription WHERE podcastId is :id LIMIT 1")
    fun findById(id: Int): Subscription?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg subscriptions: Subscription)

    @Delete
    fun delete(subscription: Subscription)
}