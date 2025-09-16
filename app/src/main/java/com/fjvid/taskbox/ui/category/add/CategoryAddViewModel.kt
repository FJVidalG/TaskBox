package com.fjvid.taskbox.ui.category.add

import android.content.res.Resources
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val resources: Resources,
) : ViewModel() {


    var categoryAddState by mutableStateOf(CategoryAddState())
        private set

    var messages = CategoryAddString(
        nameEmpty = resources.getString(R.string.name_empty_list),
        nameRepeat = resources.getString(R.string.name_repeat_list),
        colorInvalid = resources.getString(R.string.color_empty_list)
    )
        private set

    var categoryError by mutableStateOf(CategoryAddError())
        private set


    fun getCategory(id: Long) {
        viewModelScope.launch {
            val category = categoryRepository.getCategoryById(id)
            category?.let {
                categoryAddState = categoryAddState.copy(
                    id = it.id,
                    name = it.name,
                    color = it.color
                )
            }
        }
    }


    fun onNameChange(name: String) {
        categoryAddState = categoryAddState.copy(name = name)
        categoryError = categoryError.copy(nameError = null)
    }

    fun onColorChange(color: Long) {
        if (categoryAddState.id == 1L) return // para evitar que pueda cambiar el color de Default
        categoryAddState = categoryAddState.copy(color = color)

    }

    suspend fun onSave(): Boolean {
        val categories = categoryRepository.getAllCategories()

        // 1. Comprobamos que todos lo campos son correctos (el nombre no puede ser vacio, ni estar repetido)

        // validacion de nombre en blanco
        if (categoryAddState.name.isBlank()) {
            categoryError =
                categoryError.copy(nameError = messages.nameEmpty) //si está en blanco salta el mensaje de error
            return false
        }

        // validación de nombre duplicado
        val isDuplicate = categories.any {
            it.name.equals(
                categoryAddState.name,
                ignoreCase = true
            ) && it.id != categoryAddState.id // buscamos en el repositorio si alguno coincide con el estado actual
        }

        if (isDuplicate) {
            categoryError =
                categoryError.copy(duplicateError = messages.nameRepeat) // si coincide salta el mensage de error
            return false
        }

        // validacion de elegir un color
        if (categoryAddState.color == 0xFF000000 && categoryAddState.id != 1L) {
            categoryError = categoryError.copy(colorError = messages.colorInvalid)
            return false
        }

        // 2. Crear la lista
        val category = Category(
            id = categoryAddState.id,
            name = categoryAddState.name.trim(),
            color = categoryAddState.color,
        )

        // 3. Añadir en el repositorio y mostrar respuesta al añadir o modificar
        if (category.id == 0L) { // si la lista tiene id 0 significa que es nuevo por lo que se llama a la funcion add si no a update

            categoryRepository.addCategory(category)

        } else {

            categoryRepository.updateCategory(category)

        }
        return true
    }
}