package com.robj.betterpod.networking.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*

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
    @Serializable(with = JsonAsStringSerializer::class)
    var categories: List<Category> = emptyList()
) {

    object JsonAsStringSerializer :
        JsonTransformingSerializer<List<Category>>(tSerializer = ListSerializer(Category.serializer())) {
        override fun transformDeserialize(element: JsonElement): JsonElement {
            return JsonArray(content = element.jsonObject.map { entry ->
                JsonObject(
                    mapOf(
                        "id" to JsonPrimitive(
                            entry.key.toIntOrNull()
                        ), "name" to entry.value
                    )
                )
            })
        }
    }

}

