package com.fjvid.taskbox

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fjvid.taskbox.navigation.BottomNavItem
import com.fjvid.taskbox.navigation.categoryGraph
import com.fjvid.taskbox.navigation.taskGraph
import com.fjvid.taskbox.ui.category.add.CategoryAddViewModel
import com.fjvid.taskbox.ui.category.list.CategoryListViewModel
import com.fjvid.taskbox.ui.components.utils.BottomNavigationBar
import com.fjvid.taskbox.ui.notification.NotificationPermissionDialog
import com.fjvid.taskbox.ui.notification.NotificationViewModel
import com.fjvid.taskbox.ui.task.add.TaskAddViewModel
import com.fjvid.taskbox.ui.task.details.TaskDetailsViewModel
import com.fjvid.taskbox.ui.task.list.TaskListViewModel

/**
 * Pantalla de inicio con el NavigationBar
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    var showNotificationDialog by remember { mutableStateOf(true) }
    if (showNotificationDialog) {
        NotificationPermissionDialog(
            onDismiss = {
                showNotificationDialog = false // cuando se cancela o se cierra el diálogo
            }
        )
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Categories.route,
            modifier = Modifier.padding(bottom = 79.dp)
        ) {

            categoryGraph(navController)
            taskGraph(navController)

        }

        hiltViewModel<CategoryAddViewModel>()
        hiltViewModel<CategoryListViewModel>()
        hiltViewModel<TaskAddViewModel>()
        hiltViewModel<TaskListViewModel>()
        hiltViewModel<TaskDetailsViewModel>()
        hiltViewModel<NotificationViewModel>()

    }
}