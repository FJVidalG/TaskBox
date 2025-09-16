package com.fjvid.taskbox.ui.category.list

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Order
import com.fjvid.taskbox.data.repository.CategoryRepository
import com.fjvid.taskbox.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
    private val resources: Resources
) : ViewModel() {


    var categoryListState by mutableStateOf(CategoryListState())
        private set

    var messages = CategoryListStrings(
        deleteCategory = resources.getString(R.string.delete_restriction),
        deleteDefault = resources.getString(R.string.delete_restriction_default)

    )
        private set

    var categoryError by mutableStateOf(CategoryListError())
        private set

    var stateUI by mutableStateOf(CategoryListUI())
    private set

    fun getCategories() {
        categoryListState = categoryListState.copy(
            isLoading = true

        )

        viewModelScope.launch {
            val categories = categoryRepository.getAllCategories()
            categoryListState = categoryListState.copy(
                isLoading = false,
                isEmpty = categories.isEmpty(),
                categories = categories
            )
        }
    }

    suspend fun onDelete(category: Category): Boolean {

        val tasks = taskRepository.getTasksByCategory(category.id)
        // validamos el nombre vacio
        if (!tasks.isNullOrEmpty()) {
            categoryError = categoryError.copy(
                deleteCategory = messages.deleteCategory
            )
            return false
        } else {
            categoryRepository.deleteCategory(category)
            getCategories()
            return true
        }
    }

    /**
     * funcion para ordenar por nombre
     */
    fun onSortChange() {
        val order = when (stateUI.order) { // cambiamos el orden
            Order.ASCENDING -> Order.DESCENDING
            Order.DESCENDING -> Order.ASCENDING
        }
        val sortedList = when (order) { // ordenamos segun el orden que toca
            Order.ASCENDING -> categoryListState.categories.sortedBy { it.name.lowercase() }
            Order.DESCENDING -> categoryListState.categories.sortedByDescending { it.name.lowercase() }
        }
        stateUI = stateUI.copy(order = order)
        categoryListState = categoryListState.copy(categories = sortedList) // aplicamos la lista ordenada en el estdo
    }

}

