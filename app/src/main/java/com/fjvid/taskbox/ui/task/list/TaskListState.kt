package com.fjvid.taskbox.ui.task.list

import androidx.compose.ui.graphics.Color
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.data.models.TaskWithCategory


data class TaskListState (
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val tasks: List<TaskWithCategory> = emptyList(),
    val filteredTasks: List<TaskWithCategory> = emptyList()
)
