package com.fjvid.taskbox.ui.task.details

import androidx.compose.ui.graphics.Color
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import java.util.Date

data class TaskDetailsState(
    val name: String = "",
    val description: String = "",
    val startDate: Date = Date(),
    val tags: List<String> = emptyList(),
    val status: Status = Status.PENDING,
    val categoryId: Long = 0L,
    val priority: Priority = Priority.LOW,
    val categoryColor: Color = Color(0xFF66BB6A),
)
