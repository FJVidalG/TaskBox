package com.fjvid.taskbox.ui.task.list

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Order
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.ui.components.dialogs.ShowDeleteTaskDialog
import com.fjvid.taskbox.ui.components.layouts.TaskItem
import com.fjvid.taskbox.ui.components.screens.LoadingScreen
import com.fjvid.taskbox.ui.components.screens.NoDataTask
import com.fjvid.taskbox.ui.components.utils.TagSearchBar

/**
 * Listado de tareas
 */
@Composable
fun TaskListScreen(
    categoryId: Long,
    onBack: () -> Unit,
    goToAddTask: (Long?) -> Unit,
    goToTaskInfo: (Long) -> Unit
) {

    val viewModel: TaskListViewModel = hiltViewModel()
    val state = viewModel.taskListState
    val stateUI = viewModel.stateUI

    LaunchedEffect(Unit) {
        viewModel.getTasks(categoryId)
    }


    val events = TaskListEvents(
        onBack = onBack,
        goToTaskForm = goToAddTask,
        goToTaskInfo = goToTaskInfo,
        onDelete = viewModel::deleteTask,
        onSortChange = viewModel::onSortChange,
        onSearchToggle = viewModel::onSearchToggle,
        onSearchChange = viewModel::onSearchChange
    )

    when {
        state.isLoading -> LoadingScreen()
        state.isEmpty -> NoDataTask { goToAddTask(0L) }
        else -> TaskListScreenContent(
            state, stateUI, events, categoryId
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreenContent(
    state: TaskListState, stateUI: TaskListUI, events: TaskListEvents, categoryId: Long
) {
    var taskToDelete by remember { mutableStateOf<Task?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { events.goToTaskForm(0L) },
                modifier = Modifier.size(48.dp),
                elevation = FloatingActionButtonDefaults.elevation(50.dp),
                shape = CircleShape,
                contentColor = Color.Blue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_item),
                    tint = Color.White
                )

            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.tasks), color = Color.White, modifier = Modifier.padding(start = 80.dp))
                    }
                },
                actions = {
                    TagSearchBar(
                        isSearching = stateUI.isSearching,
                        query = stateUI.search,
                        onToggle = events.onSearchToggle,
                        onQueryChange = events.onSearchChange
                    )
                    IconButton(onClick = { events.onSortChange() }) {
                        Icon(
                            painter = painterResource(
                                id = when (stateUI.order) {
                                    Order.ASCENDING -> R.drawable.ordenar_alfa
                                    Order.DESCENDING -> R.drawable.ordenar_alfa_hacia_abajo
                                }
                            ),
                            contentDescription = when (stateUI.order) {
                                Order.ASCENDING -> stringResource(R.string.order_asc)
                                Order.DESCENDING -> stringResource(R.string.order_desc)
                            },
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(paddingValues) // evita que el contenido quede debajo del app bar
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stateUI.categoryName,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                LazyColumn {
                    itemsIndexed(state.filteredTasks) { index, taskWithCategory ->
                        TaskItem(
                            task = taskWithCategory.task,
                            categoryColor = taskWithCategory.category.color,
                            onItemClick = { events.goToTaskInfo(taskWithCategory.task.id) },
                            onEditClick = { events.goToTaskForm(taskWithCategory.task.id) },
                            onDeleteClick = {
                                taskToDelete = taskWithCategory.task
                                showDeleteDialog = true
                            }
                        )
                        if (showDeleteDialog && taskToDelete != null) {
                            ShowDeleteTaskDialog(
                                showDialog = true,
                                task = taskToDelete!!,
                                onDismiss = {
                                    showDeleteDialog = false
                                    taskToDelete = null
                                },
                                onConfirm = {
                                    events.onDelete(taskToDelete!!)
                                    showDeleteDialog = false
                                    taskToDelete = null
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

