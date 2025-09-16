package com.fjvid.taskbox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.fjvid.taskbox.R

sealed class BottomNavItem(
    val titleResource: Int,
    val icon: ImageVector,
    val route: String
) {

    object Categories : BottomNavItem(
        R.string.categories,
        Icons.AutoMirrored.Filled.List,
        NavigationItem.CategoryGraph.ROUTE
    )

    object Tasks : BottomNavItem(
        R.string.tasks,
        Icons.Default.CheckCircle,
        NavigationItem.TaskGraph.ROUTE
    )

    companion object {
        val items = listOf(Categories, Tasks)
    }
}