package com.robj.betterpod.database

import androidx.room.TypeConverter
import com.robj.betterpod.networking.models.Category
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomConverters {

    @TypeConverter
    fun categoryToString(category: Category): String {
        return Json.encodeToString(category)
    }

    @TypeConverter
    fun stringToCategory(str: String): Category {
        return Json.decodeFromString(str)
    }

    @TypeConverter
    fun categoryListToString(list: List<Category>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun stringToCategoryList(str: String): List<Category> {
        return Json.decodeFromString(str)
    }

}