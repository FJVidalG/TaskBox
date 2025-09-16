package com.fjvid.taskbox.ui.task.list

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.data.repository.CategoryRepository
import com.fjvid.taskbox.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Order
import com.fjvid.taskbox.data.models.TaskWithCategory

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val resources: Resources,
) : ViewModel() {

    var taskListState by mutableStateOf(TaskListState())
        private set

    var stateUI by mutableStateOf(TaskListUI())
        private set

    private var currentCategoryId: Long = 0L

    fun getTasks(categoryId: Long) {
        viewModelScope.launch {
            taskListState = taskListState.copy(
                isLoading = true
            )
            val allTasksWithCategories = taskRepository.getTasksWithCategory()
            val filteredTasksWithCategories: List<TaskWithCategory>
            val currentCategory: Category?
            // Obtenemos las tareas
            if (categoryId != 0L) {
                filteredTasksWithCategories =
                    allTasksWithCategories.filter { it.task.categoryId == categoryId }
                currentCategory = categoryRepository.getCategoryById(categoryId)
            } else {
                filteredTasksWithCategories = allTasksWithCategories
                currentCategory = null
            }

            taskListState = taskListState.copy(
                isLoading = false,
                isEmpty = filteredTasksWithCategories.isEmpty(),
                tasks = filteredTasksWithCategories,
                filteredTasks = filteredTasksWithCategories
            )

            //  actualizamos taskListUI basado elas tareas filtradas
            if (categoryId != 0L && currentCategory != null) {
                stateUI = stateUI.copy(
                    categoryName = currentCategory.name,
                    categoryColor = Color(currentCategory.color)
                )
            } else { // en caso de que el cateogryId sea 0 se establece para la vista de Todas las tareas
                stateUI = stateUI.copy(
                    categoryName = resources.getString(R.string.all_tasks),
                    categoryColor = Color(0xFF4CAF50)
                )
            }
        }
    }

    fun onSortChange() {
        val order = when (stateUI.order) {
            Order.ASCENDING -> Order.DESCENDING
            Order.DESCENDING -> Order.ASCENDING
        }
        val sortedList = when (order) {
            Order.ASCENDING -> taskListState.tasks.sortedBy { it.task.name.lowercase() }
            Order.DESCENDING -> taskListState.tasks.sortedByDescending { it.task.name.lowercase() }
        }
        taskListState = taskListState.copy(
            tasks = sortedList,
            filteredTasks = if (stateUI.search.isBlank()) sortedList //actualizamos dependiendo de si la bsuqueda esta en blanco o no
            else sortedList.filter { it.task.tags.any { tag -> tag.contains(stateUI.search, true) } }
        )
        stateUI = stateUI.copy(order = order)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            getTasks(currentCategoryId)
        }
    }

    /**
     * Funcion para abrir el filtro
     */
    fun onSearchToggle() {
        stateUI = stateUI.copy(
            isSearching = !stateUI.isSearching,
            search = if (stateUI.isSearching) "" else stateUI.search // limpiamos el filtro
        )
    }

    /**
     * Funcion para aplicar la busqueda
     */
    fun onSearchChange(tag: String) {
        stateUI = stateUI.copy(search = tag)
        val filtered = if (stateUI.search.isBlank()) {
            taskListState.tasks // si el textfield esta vacío buscamos todas las tareas
        } else {  //filtramos las tareas y nos quedamos solo con las que contienen ese tag
            taskListState.tasks.filter { taskWithCategory ->
                taskWithCategory.task.tags.any { tag ->
                    tag.contains(stateUI.search, ignoreCase = true)
                }
            }
        }

        taskListState = taskListState.copy(filteredTasks = filtered) // actualizamos el estado con la lista filtrada
    }

}