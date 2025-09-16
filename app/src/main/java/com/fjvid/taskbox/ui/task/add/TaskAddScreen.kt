package com.fjvid.taskbox.ui.task.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Priority
import com.fjvid.taskbox.data.models.Status
import com.fjvid.taskbox.ui.components.utils.ChipInputField
import com.fjvid.taskbox.ui.components.utils.RestrictedDatePicker
import com.fjvid.taskbox.ui.notification.createNotification
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Creación y edicion de tareas
 */
@Composable
fun TaskAddScreen(taskId: Long, onBack: () -> Unit) {
    val viewModel: TaskAddViewModel = hiltViewModel()
    val state = viewModel.taskState
    val errorState = viewModel.taskError

    LaunchedEffect(Unit) {
        viewModel.getTask(taskId)
    }

    val events = TaskAddEvents(
        onBack = onBack,
        onStatusChange = viewModel::onStatusChange,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onCategoryChange = viewModel::onCategoryChange,
        onPriorityChange = viewModel::onPriorityChange,
        onDateChange = viewModel::onDateChange,
        onTagsChange = viewModel::onTagsChange,
        onSave = viewModel::onSave
    )

    val messages = viewModel.messages

    TaskAddScreenContent(state, events, errorState, messages)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddScreenContent(
    state: TaskAddState, events: TaskAddEvents, errorState: TaskAddError, messages: TaskAddString

) {

    val showDatePicker = rememberSaveable { mutableStateOf(false) }
    val expandedPriorty = rememberSaveable { mutableStateOf(false) }
    val expandedStatus = rememberSaveable { mutableStateOf(false) }
    val expandedCategory = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = when(state.id) {
                            0L -> stringResource(R.string.create_task)
                            else -> stringResource(R.string.edit_task)}, color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )

        }
    , ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {

                OutlinedTextField(
                    value = state.name,
                    onValueChange = events.onNameChange,
                    label = { Text(text = stringResource(R.string.task_name_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    isError = errorState.nameError != null || errorState.duplicateError != null,
                    supportingText = {
                        when {
                            errorState.nameError != null -> Text(messages.nameEmpty)
                            errorState.duplicateError != null -> Text(messages.nameRepeat)
                        }
                    }
                )

                OutlinedTextField(
                    value = state.description,
                    onValueChange = events.onDescriptionChange,
                    label = { Text(text = stringResource(R.string.description_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp)
                        .padding(horizontal = 8.dp),
                    maxLines = 5,
                )

                ExposedDropdownMenuBox(
                    expanded = expandedStatus.value,
                    onExpandedChange = { change -> expandedStatus.value = change },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp),
                ) {
                    TextField(
                        readOnly = true,
                        value = when (state.status) {
                            Status.COMPLETED -> stringResource(R.string.status_completed)
                            Status.PENDING -> stringResource(R.string.status_pending)
                            Status.CANCELED -> stringResource(R.string.status_canceled)
                        },
                        onValueChange = {},
                        label = { Text(stringResource(R.string.status)) },
                        trailingIcon = { TrailingIcon(expanded = expandedStatus.value) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedStatus.value,
                        onDismissRequest = { expandedStatus.value = false }
                    ) {
                        Status.entries.forEach { status ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (status) {
                                            Status.COMPLETED -> stringResource(R.string.status_completed)
                                            Status.PENDING -> stringResource(R.string.status_pending)
                                            Status.CANCELED -> stringResource(R.string.status_canceled)
                                        }
                                    )
                                },
                                onClick = {
                                    events.onStatusChange(status)
                                    expandedStatus.value = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedCategory.value,
                    onExpandedChange = { change -> expandedCategory.value = change },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp),
                ) {
                    TextField(
                        readOnly = true,
                        value = state.categoryName,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.category)) },
                        trailingIcon = { TrailingIcon(expanded = expandedCategory.value) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategory.value,
                        onDismissRequest = { expandedCategory.value = false }
                    ) {
                        val categories = state.categories
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    events.onCategoryChange(category)
                                    expandedCategory.value = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedPriorty.value,
                    onExpandedChange = { change -> expandedPriorty.value = change },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp),
                ) {
                    TextField(
                        readOnly = true,
                        value = when (state.priority) {
                            Priority.LOW -> stringResource(R.string.low_priority)
                            Priority.MEDIUM -> stringResource(R.string.medium_priority)
                            Priority.HIGHT -> stringResource(R.string.high_priority)
                            Priority.URGENT -> stringResource(R.string.urgent_priority)
                        },
                        onValueChange = {},
                        label = { Text(stringResource(R.string.priority_label)) },
                        trailingIcon = { TrailingIcon(expanded = expandedPriorty.value) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedPriorty.value,
                        onDismissRequest = { expandedPriorty.value = false }
                    ) {
                        Priority.entries.forEach { priority ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (priority) {
                                            Priority.LOW -> stringResource(R.string.low_priority)
                                            Priority.MEDIUM -> stringResource(R.string.medium_priority)
                                            Priority.HIGHT -> stringResource(R.string.high_priority)
                                            Priority.URGENT -> stringResource(R.string.urgent_priority)
                                        }
                                    )
                                },
                                onClick = {
                                    events.onPriorityChange(priority)
                                    expandedPriorty.value = false
                                }
                            )
                        }
                    }
                }
                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    OutlinedTextField(
                        value = dateFormatter.format(state.startDate),
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f),
                        label = { Text(stringResource(R.string.date)) },

                        )
                    IconButton(
                        onClick = { showDatePicker.value = true },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = stringResource(R.string.select_date),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                if (showDatePicker.value) {
                    val currentDate = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.timeInMillis
                    RestrictedDatePicker(
                        currentDate = currentDate,
                        initialDate = state.startDate.time,
                        onDateSelected = { selectedDate ->
                            events.onDateChange(Date(selectedDate))
                        },
                        onDismiss = { showDatePicker.value = false }
                    )
                }

                Column(
                ) {
                    ChipInputField(
                        tags = state.tags,
                        onTagAdded = { tag ->
                            if (tag.isNotBlank() && tag !in state.tags) { // si el tag no esta en blanco o no está ya en la lista de tags se añade
                                events.onTagsChange(state.tags + tag)
                            }
                        },
                        onTagRemoved = { tag ->
                            events.onTagsChange(state.tags - tag) // eliminamos el tag
                        }
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val error = events.onSave(context)
                            if (error) {
                                events.onBack()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(stringResource(if (state.id != 0L) R.string.update_task_button else R.string.create_task))
                }
            }
        }
    }
}

