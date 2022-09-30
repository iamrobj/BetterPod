package com.robj.betterpod.networking.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Episode(
    @PrimaryKey
    val id: Long,
    val title: String,
    val link: String,
    val description: String,
    val guid: String,
    val datePublished: Long,
    val datePublishedPretty: String,
    val dateCrawled: Long,
    val enclosureUrl: String,
    val enclosureType: String,
    val enclosureLength: Long,
    val duration: Long,
    val explicit: Long,
    val episode: Long?,
    val episodeType: String,
    val season: Long,
    val image: String,
    val feedItunesId: Long,
    val feedImage: String,
    val feedId: Long,
    val feedLanguage: String,
    val feedDead: Long,
    val feedDuplicateOf: String?,
    val chaptersUrl: String?,
    val transcriptUrl: String?
)