package com.fjvid.taskbox.ui.task.details


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import com.fjvid.taskbox.ui.components.layouts.InfoRow
import com.fjvid.taskbox.ui.components.utils.TaskPriorityIcon

/**
 * Información ampliada de la tarea
 */
@Composable
fun TaskDetailsScreen(taskId: Long, onBack: () -> Unit) {

    val viewModel: TaskDetailsViewModel = hiltViewModel()
    val state = viewModel.taskDetailsState

    LaunchedEffect(Unit) {
        viewModel.getTask(taskId)
    }

    val events = TaskDetailsEvents(
        onBack = onBack,
        getPriorityIcon = viewModel::getPriorityIcon,
        getPriorityLabel = viewModel::getPriorityLabel,
        formatStartDate = viewModel::formatStartDate
    )

    TaskDetailsScreenContent(
        state, events
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreenContent(
    state: TaskDetailsState, events: TaskDetailsEvents
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(R.string.details), color = Color.White)
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
            modifier = Modifier.padding(paddingValues)
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
                        text = state.name,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )

                    TaskPriorityIcon(
                        status = state.status,
                        modifier = Modifier.size(32.dp)
                    )

                }

                Text(
                    text = state.description,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = state.categoryColor
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoRow(
                            icon = Icons.Default.DateRange,
                            label = stringResource(R.string.start_date_label),
                            value = events.formatStartDate(),
                            iconSize = 32.dp,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp,
                        )
                        InfoRow(
                            icon = events.getPriorityIcon(),
                            label = stringResource(R.string.priority_label),
                            value = events.getPriorityLabel(),
                            iconSize = 32.dp,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp,
                        )
                    }
                }
                if (state.tags.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.tags_header),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.tags.forEach { tag ->
                            AssistChip(
                                onClick = {},
                                label = { Text(tag) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.etiqueta_de_precio),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

