package com.fjvid.taskbox.data

import androidx.compose.runtime.mutableStateListOf

object TaskRepository {
    private val _tasks = mutableStateListOf<Task>() //el mutableStateListOf crea una lista que se actualiza cuando alguno de sus elementos cambia
    private val _lists =
        mutableStateListOf<Category>().apply { add(Category(0, "Default", 0xFF66BB6A)) }

    val tasks: List<Task> get() = _tasks //crea una lista de tasks inmutable a partir de la mutable privada
    val lists: List<Category> get() = _lists //lo mismo pero para taskslists

    fun addTask(task: Task) {
        require(_tasks.none({ it.name == task.name })) { "Ya existe una tarea con ese nombre" } //none recorre la lista y comprueba que ningun elemento cumpla la condición
        _tasks.add(task)
    }


    fun updateTask(task: Task) {
        val index =
            _tasks.indexOfFirst { it.id == task.id } //devuelve el indice del primer elemento de la lista que coincida con la condición
        if (index != -1) _tasks[index] = task // el task que coincida será e nuevo valor del _tasks
    }

    fun addTaskList(taskList: Category){
        require(_lists.none({it.name == taskList.name})) { "Ya existe una lista con ese nombre" }
        _lists.add(taskList)
    }

    fun updateTaskList(taskList: Category){
        val index = _lists.indexOfFirst { it.id == taskList.id }
        if (index != -1) _lists[index] = taskList
    }
}