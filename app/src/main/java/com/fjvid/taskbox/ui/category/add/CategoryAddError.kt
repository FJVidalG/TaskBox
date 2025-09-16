package com.fjvid.taskbox.ui.category.add

data class CategoryAddError (
    val nameError: String? = null,
    val duplicateError: String? = null,
    val colorError: String? = null
)