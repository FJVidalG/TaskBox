package com.fjvid.taskbox.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.R
import com.fjvid.taskbox.ui.components.DatePickerField
import com.fjvid.taskbox.ui.components.PrioritySelector
import com.fjvid.taskbox.data.Task
import com.fjvid.taskbox.data.TaskRepository
import com.fjvid.taskbox.ui.theme.TaskBoxTheme
import java.util.Date


@Composable
fun TaskFormScreen(modifier: Modifier = Modifier, listId: Long, taskId: Long? = null) {
    val task = taskId?.let { id -> TaskRepository.tasks.firstOrNull { it.id == id } }

    var id by remember { mutableStateOf(task?.id ?: 0) }
    var name by remember { mutableStateOf(task?.name ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var priority by remember { mutableStateOf(task?.priority ?: 1) }
    var tags by remember { mutableStateOf(task?.tags?.joinToString(", ") ?: "") }
    var startDate by remember { mutableStateOf(task?.startDate ?: Date()) }
    var isCompleted by remember { mutableStateOf(task?.isCompleted ?: false) }
    var selectListId by remember { mutableStateOf(task?.listId ?: listId) }


    val handleSave: () -> Unit = {
        val newTask = Task(
            id = task?.id ?: (TaskRepository.tasks.maxOfOrNull { it.id }?.plus(1) ?: 0),
            name = name,
            description = description,
            startDate = startDate,
            tags = tags.split(",").map { it.trim() },
            isCompleted = isCompleted,
            listId = selectListId,
            priority = priority,
        )
        if (task != null) {
            TaskRepository.updateTask(newTask)
        } else {
            TaskRepository.addTask(newTask)
        }

    }
    TaskFormScreenContent(
        modifier,
        task,
        onNameChange = { name = it },
        onDescriptionChange = { description = it },
        onPriorityChange = { priority = it },
        onTagsChange = { tags = it },
        onDateChange = { startDate = it },
        onCompletedChange = { isCompleted = it },
        isEditing = task != null,
        handleSave = handleSave
    )
}

@Composable
fun TaskFormScreenContent(
    modifier: Modifier = Modifier,
    task: Task?,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Int) -> Unit,
    onTagsChange: (String) -> Unit,
    onDateChange: (Date) -> Unit,
    onCompletedChange: (Boolean) -> Unit,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = task?.isCompleted ?: false,
                onCheckedChange = onCompletedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = stringResource(R.string.completed_label),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        TextField(
            value = task?.name ?: "",
            onValueChange = onNameChange,
            label = { Text(text = stringResource(R.string.task_name_hint)) },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = task?.description ?: "",
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(R.string.description_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), maxLines = 3
        )
        PrioritySelector(
            currentPriority = task?.priority ?: 0,
            onPrioritySelected = onPriorityChange
        )

        DatePickerField(
            label = stringResource(R.string.start_date_label),
            selectedDate = task?.startDate ?: Date(),
            onDateSelected = onDateChange
        )


        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(stringResource(R.string.tags_label), style = MaterialTheme.typography.bodyMedium)
            TextField(
                value = task?.tags.toString() ?: "",
                onValueChange = onTagsChange,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                handleSave()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(stringResource(if (isEditing) R.string.update_task_button else R.string.create_task_button))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TaskFormPreview_Create() {
    TaskBoxTheme {
        Surface {
            TaskListFormScreen(
                listId = 1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskFormPreview_Edit() {
    val testTask = Task(
        id = 999,
        name = "Tarea de prueba",
        description = "Descripción de ejemplo",
        startDate = Date(),
        tags = listOf("urgente", "importante"),
        isCompleted = false,
        listId = 1,
        priority = 3
    )
    TaskRepository.addTask(testTask)

    TaskBoxTheme {
        Surface {
            TaskFormScreen(
                listId = 1,
                taskId = 999,
            )
        }
    }
}