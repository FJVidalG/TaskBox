package com.fjvid.taskbox.data.converter

import androidx.room.TypeConverter
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Gson().toJson(list) // cambiamos a Json
    }

    @TypeConverter
    fun toStringList(list: String): List<String> {
        val type = object : TypeToken<List<String>>(){}.type // obtenemos el tipo
        return Gson().fromJson(list, type) // cambiamos de Json a la lista de string
    }

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromStatus(status: Status): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(status: String): Status {
        return Status.valueOf(status)
    }
}