package com.robj.betterpod.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subscription(
    @PrimaryKey
    val podcastId: Int
)