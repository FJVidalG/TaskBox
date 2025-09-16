package com.fjvid.taskbox.ui.task.details

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.repository.CategoryRepository
import com.fjvid.taskbox.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val resources: Resources
) : ViewModel() {

    var taskDetailsState by mutableStateOf(TaskDetailsState())

    fun getTask(taskId: Long) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            task?.let {
                val category = categoryRepository.getCategoryById(it.categoryId)
                val color = Color(category?.color ?: 0xFF66BB6A)
                taskDetailsState = taskDetailsState.copy(
                    name = it.name,
                    description = it.description,
                    status = it.status,
                    startDate = it.startDate,
                    tags = it.tags,
                    priority = it.priority,
                    categoryId = it.categoryId,
                    categoryColor= color
                )
            }
        }
    }


    fun getPriorityIcon(): ImageVector {
        return when (taskDetailsState.priority) {
            Priority.LOW -> Icons.Default.Info
            Priority.MEDIUM -> Icons.Default.Star
            Priority.HIGHT -> Icons.Default.Warning
            Priority.URGENT -> Icons.Default.Lock
        }
    }

    fun getPriorityLabel(): String {
        return when (taskDetailsState.priority) {
            Priority.LOW -> resources.getString(R.string.low_priority)
            Priority.MEDIUM -> resources.getString(R.string.medium_priority)
            Priority.HIGHT -> resources.getString(R.string.high_priority)
            Priority.URGENT -> resources.getString(R.string.urgent_priority)
        }
    }

    fun formatStartDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(taskDetailsState.startDate)
    }

}