package me.kofesst.android.vkqualfirst.features.domain.repositories

import me.kofesst.android.vkqualfirst.features.domain.models.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
}
