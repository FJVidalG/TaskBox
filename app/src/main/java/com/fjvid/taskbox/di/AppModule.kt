package com.fjvid.taskbox.di

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fjvid.taskbox.data.TaskBoxDataBase
import com.fjvid.taskbox.data.dao.CategoryDao
import com.fjvid.taskbox.data.dao.TaskDao
import com.fjvid.taskbox.data.repository.PreferencesRepository
import com.fjvid.taskbox.data.repository.CategoryRepository
import com.fjvid.taskbox.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Extensión de Context para acceder a un DataStore llamado "taskbox_prefs".
 * Se crea automáticamente en la primera lectura/escritura.
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "taskbox_prefs")

@Module
@InstallIn (SingletonComponent::class) // SingletonComponent::class hace referencia a MainApplication
object AppModule { // objeto que va a proveer elementos mediante inyección

    /**
     * Proporciona una instancia única de la base de datos
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskBoxDataBase {
        return TaskBoxDataBase.getDatabase(context)
    }


    /**
     * Proporciona una instancia del repositorio CategoryRepository
     */

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository{
        return CategoryRepository(categoryDao)
    }


    /**
     * Proporciona una instancia del repositorio TaskRepository
     */

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepository(taskDao)
    }

    /**
     * Proporciona una instancia del DAO TaskDao
     */
    @Provides
    @Singleton
    fun provideTaskDao(taskBoxDataBase: TaskBoxDataBase): TaskDao {
        return taskBoxDataBase.taskDao()
    }

    /**
     * Proporciona una instancia del DAO CategoryDao
     */
    @Provides
    @Singleton
    fun provideCategoryDao(taskBoxDataBase: TaskBoxDataBase): CategoryDao {
        return taskBoxDataBase.categoryDao()
    }

    /**
     * Resources
     * Se utiliza este objeto para recoger los mensajes/cadenas de texto
     * declarados en R.string e internacionalizar los mensajes
     */
    @Provides
    @Singleton
    fun resources(@ApplicationContext context: Context): Resources{
        return context.resources
    }

    /**
     * Proporciona una instancia de DataStore<Preferences> del contexto del proyecto
     */
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    /**
     * Proporciona una instancia de PreferencesRepository para gestionar el DataStore
     */
    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository {
        return PreferencesRepository(dataStore)
    }

}