package com.fjvid.taskbox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.data.models.TaskWithCategory

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTasksById(taskId: Long): Task?

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    suspend fun getTasksByCategory(categoryId: Long): List<Task>?

    @Transaction
    @Query("SELECT * FROM tasks ")
    suspend fun getTasksWithCategory(): List<TaskWithCategory>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id=:id")
    suspend fun getTasksByIdWithCategory(id: Long): TaskWithCategory

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}