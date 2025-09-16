package com.fjvid.taskbox.ui.task.add

import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import java.util.Date


data class TaskAddState (
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val startDate: Date = Date(),
    val tags: List<String> = emptyList(),
    val status: Status = Status.PENDING,
    val categoryId: Long = 0,
    val priority: Priority = Priority.LOW,
    val categoryName: String = "",
    val categories: List<Category> = emptyList()
)
