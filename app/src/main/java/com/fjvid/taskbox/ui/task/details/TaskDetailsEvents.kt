package com.fjvid.taskbox.ui.task.details

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TaskDetailsEvents (
    val onBack: () -> Unit,
    val getPriorityIcon: () -> ImageVector,
    val getPriorityLabel: () -> String,
    val formatStartDate: () -> String
)