package me.kofesst.android.vkqualfirst.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kofesst.android.vkqualfirst.features.data.repositories.FakeCategoriesRepository
import me.kofesst.android.vkqualfirst.features.domain.models.Category
import me.kofesst.android.vkqualfirst.features.domain.repositories.CategoriesRepository

class ChooseCategoriesViewModel : ViewModel() {
    private val categoriesRepository: CategoriesRepository

    init {
        categoriesRepository = FakeCategoriesRepository()
    }

    private val _categoriesState = mutableStateOf<List<Category>>(emptyList())
    val categoriesState: State<List<Category>> = _categoriesState

    private val _chosenCategoriesState = mutableStateOf<List<Category>>(emptyList())
    val chosenCategoriesState: State<List<Category>> = _chosenCategoriesState

    fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = categoriesRepository.getCategories()
        }
    }

    fun onCategorySelect(category: Category) {
        _chosenCategoriesState.value += category
    }

    fun onCategoryUnselect(category: Category) {
        _chosenCategoriesState.value -= category
    }
}
