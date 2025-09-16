package com.fjvid.taskbox.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.fjvid.taskbox.data.dao.CategoryDao
import com.fjvid.taskbox.data.models.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    suspend fun addCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

    suspend fun getCategoryById(categoryId: Long): Category? {
        return categoryDao.getCategoryById(categoryId)
    }

    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category)
    }
}