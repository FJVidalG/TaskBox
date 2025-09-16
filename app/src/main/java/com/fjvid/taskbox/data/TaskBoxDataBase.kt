package com.fjvid.taskbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fjvid.taskbox.data.converter.Converters
import com.fjvid.taskbox.data.dao.CategoryDao
import com.fjvid.taskbox.data.dao.TaskDao
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Task
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [Task::class, Category::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TaskBoxDataBase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    companion object {
        @Volatile
        private var INSTANCE: TaskBoxDataBase? = null

        fun getDatabase(context: Context): TaskBoxDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskBoxDataBase::class.java,
                    "taskbox_database.db"
                )
                    // Callback para pre-poblar la base de datos
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Se utiliza un executor para realizar la inserción en un hilo de fondo
                            //Las tareas se ejecutan de forma secuencial en un hilo/s
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    val categoryDao = database.categoryDao()
                                    runBlocking { // runBlocking bloquea el hilo actual hasta que la corrutina termina
                                        categoryDao.insertCategory(Category.CategoryDefault) // al crear la base de datos insertamos la categoria Default
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

