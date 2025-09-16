package com.fjvid.taskbox.data.repository

import com.fjvid.taskbox.data.dao.TaskDao
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.data.models.TaskWithCategory
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    suspend fun getTasksWithCategory(): List<TaskWithCategory>{
        return taskDao.getTasksWithCategory()
    }

    suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTasksById(taskId)
    }

    suspend fun getTasksByCategory(categoryId: Long): List<Task>?{
        return  taskDao.getTasksByCategory(categoryId)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}