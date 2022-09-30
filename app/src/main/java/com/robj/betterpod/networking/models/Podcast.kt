package com.robj.betterpod.networking.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Podcast(
    @PrimaryKey
    val id: Int,
    val url: String,
    val title: String,
    val description: String,
    val author: String,
    val image: String,
    val artwork: String,
    val newestItemPublishTime: Int? = null,
    val itunesId: Int? = null,
    val trendScore: Int? = null,
    val language: String,
//	val categories : Categories
)