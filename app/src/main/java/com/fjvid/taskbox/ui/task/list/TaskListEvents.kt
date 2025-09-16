package com.fjvid.taskbox.ui.task.list

import android.icu.text.CaseMap.Title
import androidx.compose.ui.graphics.Color
import com.fjvid.taskbox.data.models.Task

data class TaskListEvents (
    val onBack: () -> Unit,
    val goToTaskForm: (Long?) -> Unit,
    val goToTaskInfo: (Long) -> Unit,
    val onDelete: (Task) -> Unit,
    val onSortChange: () -> Unit,
    val onSearchToggle: () -> Unit,
    val onSearchChange: (String) -> Unit,
)