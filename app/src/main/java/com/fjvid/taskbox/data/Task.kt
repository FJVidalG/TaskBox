package com.fjvid.taskbox.data

import java.util.Date
import kotlin.collections.List

data class Task(
    val id: Long,
    val name: String,
    val description: String,
    val startDate: Date,
    val tags: List<String>,
    val isCompleted: Boolean = false,
    val listId: Long,
    val priority: Int,
)