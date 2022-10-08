package com.robj.betterpod.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    val status: String,
    val feeds: List<Category>,
    val count: Int,
    val max: String? = null,
    val since: Int? = null,
    val description: String
)