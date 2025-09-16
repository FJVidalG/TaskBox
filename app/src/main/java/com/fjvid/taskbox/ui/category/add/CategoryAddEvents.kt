package com.fjvid.taskbox.ui.category.add

data class CategoryAddEvents (
    val onBack: () -> Unit,
    val onNameChange: (String) -> Unit,
    val onColorChange: (Long) -> Unit,
    val onSave: suspend () -> Boolean
)