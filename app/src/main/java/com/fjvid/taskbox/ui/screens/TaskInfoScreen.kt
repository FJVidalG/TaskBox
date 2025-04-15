package com.fjvid.taskbox.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.data.Task
import com.fjvid.taskbox.data.TaskRepository
import com.fjvid.taskbox.ui.theme.TaskBoxTheme
import com.fjvid.taskbox.ui.components.InfoRow
import com.fjvid.taskbox.ui.components.TaskIcon
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.fjvid.taskbox.R


@Composable
fun TaskInfoScreen(modifier: Modifier = Modifier, task: Task) {
    val listColor =
        TaskRepository.lists.firstOrNull({ it.id == task.listId })?.color?.let { Color(it) }
            ?: MaterialTheme.colorScheme.surfaceVariant

    val formatterDate = remember (task.startDate) {SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(task.startDate)}

    val highPriority = stringResource(R.string.high_priority)
    val mediumPriority = stringResource(R.string.medium_priority)
    val lowPriority = stringResource(R.string.low_priority)

    val (priorityIcon, priorityText) = remember(task.priority){ // creacion de par variable Icono y String
        when (task.priority) {
            3 -> Icons.Default.Warning to highPriority // asociacion del Icono Warnig con el String Alta mediante la palabra clave to
            2 -> Icons.Default.Star to mediumPriority
            else -> Icons.Default.Info to lowPriority
        }
    }

    TaskInfoScreenContent(modifier, task, listColor, formatterDate, priorityIcon, priorityText)
}

@Composable
fun TaskInfoScreenContent(
    modifier: Modifier = Modifier,
    task: Task,
    listColor: Color,
    formatterDate: String,
    priorityIcon: ImageVector,
    priorityText: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = task.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            TaskIcon(task.isCompleted)
        }

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = listColor
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(
                    icon = Icons.Default.DateRange,
                    label = stringResource(R.string.start_date_label),
                    value = formatterDate
                )
                InfoRow(
                    icon = priorityIcon,
                    label = stringResource(R.string.priority_label),
                    value = priorityText
                )
            }
        }
        if (task.tags.isNotEmpty()) {
            Text(
                text = stringResource(R.string.tags_header),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                task.tags.forEach { tag ->
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskBoxTheme {
        TaskInfoScreen(
            task = Task(
                id = 1,
                name = "Reunión importante",
                description = "Revisión de objetivos trimestrales con el equipo",
                startDate = Date(),
                tags = listOf("Trabajo", "Urgente"),
                isCompleted = true,
                listId = 1,
                priority = 3,
            )
        )
    }
}
