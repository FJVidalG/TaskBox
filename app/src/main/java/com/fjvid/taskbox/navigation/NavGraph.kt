package com.fjvid.taskbox.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.fjvid.taskbox.navigation.NavigationItem.CategoryGraph
import com.fjvid.taskbox.navigation.NavigationItem.TaskGraph
import com.fjvid.taskbox.ui.category.add.CategoryAddScreen
import com.fjvid.taskbox.ui.category.list.CategoryListScreen
import com.fjvid.taskbox.ui.task.add.TaskAddScreen
import com.fjvid.taskbox.ui.task.details.TaskDetailsScreen
import com.fjvid.taskbox.ui.task.list.TaskListScreen


fun NavGraphBuilder.categoryGraph(navController: NavController) {
    navigation(
        startDestination = CategoryGraph.categoryListRoute(),
        route = CategoryGraph.ROUTE
    ) {
        categoryListNav(navController)
        categoryAddNav(navController)
        categoryTasksNav(navController)
    }
}


private fun NavGraphBuilder.categoryListNav(navController: NavController) {
    composable(
        route = CategoryGraph.categoryListRoute()
    ) {
        CategoryListScreen(
            goToAddCategory = {navController.navigate(CategoryGraph.categoryAddRoute(it ?: 0L)) },
            goToTasks = {navController.navigate(CategoryGraph.categoryTasksRoute(it)) })
    }

}

private fun NavGraphBuilder.categoryAddNav(navController: NavController) {
    composable(
        route = "${CategoryGraph.ROUTE}/category_add/{categoryId}",
        arguments = listOf(
            navArgument("categoryId") {
                type = NavType.LongType
                defaultValue = 0L
            }
        )
    ) { backStackEntry ->
        val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
        CategoryAddScreen(categoryId = categoryId, onBack = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.categoryTasksNav(navController: NavController) {
    composable(route = "${CategoryGraph.ROUTE}/tasks/{categoryId}", arguments = listOf(
        navArgument("categoryId") {
            type = NavType.LongType
            defaultValue = 0L
        }
    )) {
            backStackEntry ->
        val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
        TaskListScreen(
            categoryId = categoryId,
            onBack = { navController.popBackStack() },
            goToAddTask = { taskId -> navController.navigate(TaskGraph.taskAddRoute(taskId ?: 0L)) },
            goToTaskInfo = { taskId -> navController.navigate(TaskGraph.taskDetailRoute(taskId))})
    }
}

fun NavGraphBuilder.taskGraph(navController: NavController) {
    navigation(
        startDestination = TaskGraph.taskListRoute(0L),
        route = TaskGraph.ROUTE
    ) {
        taskListNav(navController)
        taskAddNav(navController)
        tasksDetailNav(navController)
    }
}


private fun NavGraphBuilder.taskListNav(navController: NavController) {
    composable(
        route = "${TaskGraph.ROUTE}/tasks/{categoryId}", arguments = listOf(
        navArgument("categoryId") {
            type = NavType.LongType
            defaultValue = 0L
        })) { backStackEntry ->
        val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
        TaskListScreen(
            categoryId = categoryId,
            onBack = { navController.popBackStack() },
            goToAddTask = { taskId -> navController.navigate(TaskGraph.taskAddRoute(taskId ?: 0L)) },
            goToTaskInfo = { taskId -> navController.navigate(TaskGraph.taskDetailRoute(taskId)) })
    }

}


private fun NavGraphBuilder.taskAddNav(navController: NavController) {
    composable(
        route = "${TaskGraph.ROUTE}/task_add/{taskId}", arguments = listOf(
            navArgument("taskId") {
                type = NavType.LongType
                defaultValue = 0L
            })
    ) { backStackEntry ->
        val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
        TaskAddScreen(taskId = taskId, onBack = { navController.popBackStack() })
    }
}


private fun NavGraphBuilder.tasksDetailNav(navController: NavController) {
    composable(
        route = "${TaskGraph.ROUTE}/task_detail/{taskId}", arguments = listOf(
            navArgument("taskId") {
                type = NavType.LongType
                defaultValue = 0L
            })
    ) { backStackEntry ->
        val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
        TaskDetailsScreen(
            taskId = taskId,
            onBack = { navController.popBackStack() })
    }
}


