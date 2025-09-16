package com.fjvid.taskbox.ui.task.add

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import com.fjvid.taskbox.data.repository.CategoryRepository
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.data.repository.TaskRepository
import com.fjvid.taskbox.ui.notification.createNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel //etiqueta para inyectar el viewmodel en toda la app
class TaskAddViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val resources: Resources,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val allCategories = categoryRepository.getAllCategories()
            taskState = taskState.copy(categories = allCategories)
        }
    }

    var taskState by mutableStateOf(TaskAddState())
        private set //Se pone un private set para evitar que se pueda modificar el objeto original. Equivalente a poner _taskState y luego acceder a este mediante un get, como ya he hecho en otras ocasiones

    var messages = TaskAddString(
        nameEmpty = resources.getString(R.string.name_empty_task),
        nameRepeat = resources.getString(R.string.name_repeat_task)
    )
        private set

    var taskError by mutableStateOf(TaskAddError())
        private set

    fun getTask(id: Long) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(id)
            val category = categoryRepository.getCategoryById(task?.categoryId ?: 0L)
            task?.let {
                taskState =
                    taskState.copy(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        startDate = it.startDate,
                        tags = it.tags,
                        status = it.status,
                        categoryId = it.categoryId,
                        priority = it.priority,
                        categoryName = category?.name ?: resources.getString(R.string.all_tasks)
                    )
            }
        }
    }


            fun onStatusChange(status: Status) {
                taskState = taskState.copy(status = status)
            }

            fun onNameChange(name: String) {
                taskState = taskState.copy(name = name)
                taskError = taskError.copy(nameError = null, duplicateError = null)
            }

            fun onDescriptionChange(description: String) {
                taskState = taskState.copy(description = description)
            }

            fun onCategoryChange(category: Category) {
                taskState = taskState.copy(categoryId = category.id, categoryName = category.name)
            }

            fun onPriorityChange(priority: Priority) {
                taskState = taskState.copy(priority = priority)
            }

            fun onDateChange(date: Date) {
                taskState = taskState.copy(startDate = date)
            }

            fun onTagsChange(tags: List<String>) {
                taskState = taskState.copy(tags = tags)
            }


           suspend fun onSave(context: Context): Boolean {

                // 1. Comprobamos que todos lo campos son correctos (el nombre no puede ser vacio, no estar repetido)

                // validacion de nombre en blanco
                if (taskState.name.isBlank()) {
                    taskError =
                        taskError.copy(nameError = messages.nameEmpty) //si está en blanco salta el mensaje de error
                    return false
                }

                // validación de nombre duplicado
                    val isDuplicate = taskRepository.getAllTasks().any {
                        it.name.equals(taskState.name, ignoreCase = true) && it.id != taskState.id
                    }

                    if (isDuplicate) {
                        taskError = taskError.copy(duplicateError = messages.nameRepeat)
                        return false
                    }

               // validación que la categoría Default existe si no se ha seleccionado ninguna
               if (taskState.categoryId == 0L) {
                   val defaultCategory = categoryRepository.getCategoryById(1L)
                   if (defaultCategory == null) {
                       //si no se encuentra la categoria Default, se crea, se añade a la base de datos y luego se asigna al task creado si no ha selecicionado otra categoria
                       categoryRepository.addCategory(
                           Category.CategoryDefault
                       )
                   }
                   taskState = taskState.copy(categoryId = Category.CategoryDefault.id)
               }

                    val task = Task(
                        id = taskState.id,
                        name = taskState.name.trim(),
                        description = taskState.description,
                        startDate = taskState.startDate,
                        tags = taskState.tags,
                        status = taskState.status,
                        categoryId = taskState.categoryId,
                        priority = taskState.priority
                    )

                    if (task.id == 0L) {
                        taskRepository.addTask(task)
                        createNotification(context) // lanzamos la notificacion cuando se crea la tarea
                    } else {
                        taskRepository.updateTask(task)
                    }

                return true
            }

    }

