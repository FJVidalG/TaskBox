package com.fjvid.taskbox.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.data.Category
import com.fjvid.taskbox.data.TaskRepository
import com.fjvid.taskbox.ui.theme.TaskBoxTheme
import com.fjvid.taskbox.R

@Composable
fun TaskListFormScreen(modifier: Modifier = Modifier, listId: Long? = null){
    val list = listId?.let { id -> TaskRepository.lists.firstOrNull { it.id == id } } //busa la lista por id, en caso de que no encuentre, el valor de list será null
    var name by remember { mutableStateOf(list?.name ?: "") }
    var selectColor by remember { mutableStateOf(list?.color ?: 0xFF66BB6A) }
    val colors = listOf(
        0xFF66BB6A, 0xFFFFA726, 0xFFEF5350,
        0xFF42A5F5, 0xFFAB47BC, 0xFF26A69A
    )

    val handleSave: () -> Unit = {
        if (list != null) { // si list no es null, queire decir que hay una lsita por lo que se llama al metodo del repostiorio para modificar las lista
            TaskRepository.updateTaskList(list.copy(name = name, color = selectColor))
        } else { // en caso contrario, si es null quiere decir que no existe la lista y por lo tanto el boton tendrá la función de crar la lista
            val newId =
                TaskRepository.lists.maxOfOrNull { it.id }?.plus(1) ?: 0
            TaskRepository.addTaskList(Category(newId, name, selectColor))
        }

        }
    TaskListFormScreenContent(
        modifier,
        name,
        onNameChange = { name = it },
        selectColor,
        onColorSelect = { selectColor = it },
        colors,
        isEditing = list != null,
        handleSave
    )
}

@Composable
fun TaskListFormScreenContent(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit,
    selectColor: Long,
    onColorSelect: (Long) -> Unit,
    colors: kotlin.collections.List<Long>,
    isEditing: Boolean,
    handleSave: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(text = stringResource(R.string.list_name_hint)) },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.list_color_label),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            colors.forEach { color -> // cada iteracion del bucle crea un box en forma circular con cada color de la lista de colores
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(color))
                        .clickable {  onColorSelect(color)  }
                        .border(
                            width = 2.dp,
                            color = if (color == selectColor) MaterialTheme.colorScheme.primary
                            else Color.Transparent,
                            shape = CircleShape
                        ))
            }
        }
        Button(
            onClick = {
                handleSave()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) { Text(stringResource (if (isEditing)  R.string.update_task_button else R.string.create_list_button)) } // si hay lista sala el texto de actualizar y si es null el de crear
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListFormPreviewNew() {
    TaskBoxTheme {
        Surface {
            TaskListFormScreen(
                listId = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListFormPreviewEdit() {
    val testList = Category(
        id = 999,
        name = "Lista de prueba",
        color = 0xFFFF0000
    )

    TaskRepository.addTaskList(testList)

    TaskBoxTheme {
        Surface {
            TaskListFormScreen(
                listId = 999,
            )
        }
    }
}