package com.fjvid.taskbox.ui.task.list

import androidx.compose.ui.graphics.Color
import com.fjvid.taskbox.data.models.Order

data class TaskListUI(
    val id: Long = 0L,
    val name: String = "",
    val categoryName: String = "",
    val categoryColor: Color = Color(0xFF66BB6A),
    val order: Order = Order.ASCENDING,
    val search: String = "",
    val isSearching: Boolean = false
)
