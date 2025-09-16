package com.fjvid.taskbox.ui.category.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Category
import com.fjvid.taskbox.data.models.Order
import com.fjvid.taskbox.ui.components.dialogs.AlertDialogError
import com.fjvid.taskbox.ui.components.layouts.CategoryItem
import com.fjvid.taskbox.ui.components.screens.LoadingScreen
import com.fjvid.taskbox.ui.components.screens.NoDataCategory
import com.fjvid.taskbox.ui.components.dialogs.ShowDeleteCategoryDialog
import kotlinx.coroutines.launch

/**
 * Listado de categorias
 */
@Composable
fun CategoryListScreen(
    goToTasks: (Long) -> Unit,
    goToAddCategory: (Long?) -> Unit,
) {
    val viewModel: CategoryListViewModel = hiltViewModel()
    val state = viewModel.categoryListState
    val errorState = viewModel.categoryError
    val stateUI = viewModel.stateUI
    val messages = viewModel.messages

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    val events = CategoryListEvents(
        goToTasks = { categoryId -> goToTasks(categoryId) },
        goToListForm = { categoryId -> goToAddCategory(categoryId) },
        onDelete = viewModel::onDelete,
        onSortChange = viewModel::onSortChange
    )

    when {
        state.isLoading -> LoadingScreen()
        state.isEmpty -> NoDataCategory { goToAddCategory(0L) }
        else -> CategoryListScreenContent(
            state, events, errorState, messages, stateUI
        )
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreenContent(
    state: CategoryListState,
    events: CategoryListEvents,
    errorState: CategoryListError,
    messages: CategoryListStrings,
    stateUI: CategoryListUI
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDeleteErrorDialog by remember { mutableStateOf(false) }
    var categoryToDelete by remember { mutableStateOf<Category?>(null) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { events.goToListForm(0L) },
                modifier = Modifier.size(48.dp),
                elevation = FloatingActionButtonDefaults.elevation(50.dp),
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
                        Text(text = stringResource(R.string.categories), color = Color.White, modifier = Modifier.padding(start = 32.dp))
                    }
                },
                actions = {
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
            LazyColumn() {
                itemsIndexed(state.categories) { index, category ->
                    CategoryItem(
                        category = category,
                        onItemClick = { events.goToTasks(category.id) },
                        onEditClick = { events.goToListForm(category.id) },
                        onDeleteClick = {
                            categoryToDelete = category
                            showDeleteDialog = true
                        }
                    )
                }
            }
            if (errorState.deleteCategory != null && showDeleteErrorDialog) {
                AlertDialogError(
                    title = stringResource(R.string.error_title),
                    message = messages.deleteCategory,
                    onDismiss = {
                        showDeleteErrorDialog = false
                    }
                )
            }
            if (showDeleteDialog && categoryToDelete != null) {
                ShowDeleteCategoryDialog(
                    showDialog = true,
                    category = categoryToDelete!!,
                    onDismiss = {
                        showDeleteDialog = false
                        categoryToDelete = null
                    },
                    onConfirm = {
                        coroutineScope.launch {
                            val success =
                                events.onDelete(categoryToDelete!!) // booleano que devuelve onDelete para saber sie ha eliminado con exito o no
                            if (!success) {
                                showDeleteErrorDialog = true
                            }
                            showDeleteDialog = false
                            categoryToDelete = null
                        }
                    }
                )
            }
        }
    }
}
