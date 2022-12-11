package me.kofesst.android.vkqualfirst.features.data.repositories

import me.kofesst.android.vkqualfirst.features.domain.models.Category
import me.kofesst.android.vkqualfirst.features.domain.repositories.CategoriesRepository

class FakeCategoriesRepository : CategoriesRepository {
    override suspend fun getCategories() = List(1) {
        listOf(
            Category("Юмор"),
            Category("Еда"),
            Category("Кино"),
            Category("Рестораны"),
            Category("Политика"),
            Category("Новости"),
            Category("Автомобили"),
            Category("Сериалы"),
            Category("Рецепты"),
            Category("Работа"),
            Category("Отдых"),
            Category("Спорт"),
        )
    }.flatten()
}
