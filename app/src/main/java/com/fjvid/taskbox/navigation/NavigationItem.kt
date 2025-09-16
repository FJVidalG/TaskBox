package com.fjvid.taskbox.navigation


sealed class NavigationItem() {

    object CategoryGraph : NavigationItem() {
        const val ROUTE = "category"

        fun categoryListRoute() = "$ROUTE/categories"
        fun categoryAddRoute(categoryId: Long) = "$ROUTE/category_add/$categoryId"
        fun categoryTasksRoute(categoryId: Long) = "$ROUTE/tasks/$categoryId"
    }

    object TaskGraph : NavigationItem() {
        const val ROUTE = "task"

        fun taskListRoute(categoryId: Long) = "$ROUTE/tasks/$categoryId"

        fun taskAddRoute(taskId: Long) = "$ROUTE/task_add/$taskId"

        fun taskDetailRoute(taskId: Long) = "$ROUTE/task_detail/$taskId"
    }

}
