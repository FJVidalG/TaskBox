package com.fjvid.taskbox.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.data.TaskRepository
import com.fjvid.taskbox.ui.components.ListItem
import com.fjvid.taskbox.data.Category
import com.fjvid.taskbox.ui.theme.TaskBoxTheme
import com.fjvid.taskbox.R

@Composable
fun ListsScreen(modifier: Modifier = Modifier){
    val lists = TaskRepository.lists

    ListsScreenContent(modifier, lists)
}


@Composable
fun ListsScreenContent(modifier: Modifier = Modifier, lists: kotlin.collections.List<Category>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn() {
            itemsIndexed(lists) { index, list ->
                ListItem(
                    list = list,
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
                                contentDescription = stringResource(R.string.add_new_list),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreen_Preview() {
    TaskBoxTheme {
        ListsScreen()
    }
}
