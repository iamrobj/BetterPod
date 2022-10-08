package com.robj.betterpod.networking.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Entity
data class Category(
    @PrimaryKey
    val id: String,
    val name: String
)