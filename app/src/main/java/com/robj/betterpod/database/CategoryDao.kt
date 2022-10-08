package com.robj.betterpod.database

import androidx.room.*
import com.robj.betterpod.networking.models.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE id IN (:categoryIds)")
    fun loadAllByIds(categoryIds: IntArray): List<Category>

    @Query("SELECT * FROM category WHERE id is :id LIMIT 1")
    fun findById(id: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg categories: Category)

    @Delete
    fun delete(category: Category)
}