package com.fjvid.taskbox.ui.task.add

import android.content.Context
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import java.util.Date

data class TaskAddEvents(
    val onBack: () -> Unit,
    val onStatusChange: (Status) -> Unit,
    val onNameChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
    val onCategoryChange: (Category) -> Unit,
    val onPriorityChange: (Priority) -> Unit,
    val onDateChange: (Date) -> Unit,
    val onTagsChange: (List<String>) -> Unit,
    val onSave: suspend (Context) -> Boolean
)