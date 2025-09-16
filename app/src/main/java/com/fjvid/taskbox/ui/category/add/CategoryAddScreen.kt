package com.fjvid.taskbox.ui.category.add

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import kotlinx.coroutines.launch

/**
 * Creación y edicion de categorías
 */
@Composable
fun CategoryAddScreen(categoryId: Long, onBack: () -> Unit) {
    val viewModel: CategoryAddViewModel = hiltViewModel()

    val state = viewModel.categoryAddState
    val errorState = viewModel.categoryError
    val messages = viewModel.messages

    LaunchedEffect(Unit) {
        viewModel.getCategory(categoryId)
    }

    val events = CategoryAddEvents(
        onBack = onBack,
        onNameChange = viewModel::onNameChange,
        onColorChange = viewModel::onColorChange,
        onSave = viewModel::onSave
    )



    CategoryAddScreenContent(
        state, events, errorState, messages
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CategoryAddScreenContent(
    state: CategoryAddState,
    events: CategoryAddEvents,
    errorState: CategoryAddError,
    messages: CategoryAddString
) {
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
                        Text(
                            text = when (state.id) {
                                0L -> stringResource(R.string.create_category)
                                else -> stringResource(R.string.edit_category)
                            }, color = Color.White
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
            modifier = Modifier.padding(paddingValues) // importante para evitar que el contenido quede debajo del app bar
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    maxLines = 1,
                    value = state.name,
                    onValueChange = events.onNameChange,
                    label = { Text(text = stringResource(R.string.list_name_hint)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = errorState.nameError != null || errorState.duplicateError != null, // is Error boleano que en caso de que se cumpla se muestra en rojo los elementos del TextField
                    supportingText = { // parametro para añadir texto auxiliar en caso de error
                        when {
                            errorState.nameError != null -> Text(messages.nameEmpty)
                            errorState.duplicateError != null -> Text(messages.nameRepeat)
                        }
                    },
                )

                Text(
                    text = stringResource(R.string.list_color_label),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    val colors = listOf(
                        0xFF66BB6A, 0xFF43A047, 0xFF42A5F5,
                        0xFF1E88E5, 0xFFAB47BC, 0xFF8E24AA,
                        0xFFFFA726, 0xFFFF7043, 0xFFEF5350,
                        0xFFE53935, 0xFF8D6E63, 0xFFB0BEC5
                    )

                    colors.forEach { color -> // cada iteracion del bucle crea un box en forma circular con cada color de la lista de colores
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(color))
                                .clickable {
                                    if (state.id == 1L) {
                                        Toast.makeText( // toast para avisar que no puede cambiar el color de default
                                            context,
                                            context.getString(R.string.default_color_not_editable),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        events.onColorChange(color)
                                    }
                                }
                                .border(
                                    width = 2.dp,
                                    color = if (color == state.color) MaterialTheme.colorScheme.primary
                                    else Color.Transparent,
                                    shape = CircleShape
                                ))
                    }
                }
                if (errorState.colorError != null) {
                    Text(
                        text = messages.colorInvalid,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val success = events.onSave()
                            if (success) {
                                events.onBack()
                            }
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) { Text(stringResource(if (state.id != 0L) R.string.update_task_button else R.string.create_category)) } // si hay lista sala el texto de actualizar y si es null el de crear
            }
        }
    }
}