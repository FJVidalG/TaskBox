package com.fjvid.taskbox.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val color: Long
){
    companion object{
        val CategoryDefault = Category(1L, "Default", 0xFF607D8B)
    }
}