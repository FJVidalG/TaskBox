package com.fjvid.taskbox.ui.category.list

import androidx.compose.ui.graphics.Color
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Order

data class CategoryListState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList()
)
