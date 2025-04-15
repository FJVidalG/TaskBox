package com.fjvid.taskbox.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.Task
import com.fjvid.taskbox.data.Category
import com.fjvid.taskbox.data.TaskRepository
import com.fjvid.taskbox.data.TaskRepository.addTask
import com.fjvid.taskbox.data.TaskRepository.addTaskList
import com.fjvid.taskbox.ui.theme.TaskBoxTheme
import com.fjvid.taskbox.ui.components.TaskItem
import java.sql.Date
import kotlin.collections.*


@Composable
fun TasksScreen(modifier: Modifier = Modifier, listId: Long? = null){
    val tasks = if (listId != null) TaskRepository.tasks.filter() { it.listId == listId } else TaskRepository.tasks

    val title = TaskRepository.lists.firstOrNull { it.id == listId }?.name ?: "Todas las tareas" // en caso de que el id no se encuentre muestra un titulo y si no muestra el titulo con el nombre de la lista de las tareas que se muestran


    TasksScreenContent(modifier, tasks, title)
}

@Composable
fun TasksScreenContent(modifier: Modifier, tasks: List<Task>, title: String) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
        LazyColumn {
            itemsIndexed(tasks) { index, task ->
                TaskItem(
                    task = task,
                    onItemClick = {},
                    onEditClick = {}
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Blue,
                        modifier = Modifier.size(48.dp)
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_item),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TasksScreenPreview() {

        addTaskList(Category(1, "Trabajo", 0xFF6200EE))
        addTaskList(Category(2, "Personal", 0xFF03DAC5))

        addTask(
            Task(
                id = 1,
                name = "Reunión importante",
                listId = 1,
                description = "Preparar presentación",
                startDate = Date(System.currentTimeMillis()),
                tags = listOf("urgente", "oficina"),
                isCompleted = false,
                priority = 3,
            )
        )

        addTask(
            Task(
                id = 2,
                name = "Comprar supermercado",
                listId = 2,
                description = "Leche y huevos",
                startDate = Date(System.currentTimeMillis()),
                tags = listOf("hogar"),
                isCompleted = true,
                priority = 1,
            )
        )


    TaskBoxTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TasksScreen(modifier = Modifier, 1)
        }
    }
}