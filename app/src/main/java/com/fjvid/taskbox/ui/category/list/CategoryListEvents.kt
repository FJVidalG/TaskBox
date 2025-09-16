package com.fjvid.taskbox.ui.category.list

import com.fjvid.taskbox.data.models.Category

data class CategoryListEvents(
    val goToTasks: (Long) -> Unit,
    val goToListForm: (Long?) -> Unit,
    val onDelete: suspend (Category) -> Boolean,
    val onSortChange: () -> Unit
)