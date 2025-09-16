package com.fjvid.taskbox.ui.components.utils

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fjvid.taskbox.R

@Composable
fun TagSearchBar(
    isSearching: Boolean,
    query: String,
    onToggle: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    if (isSearching) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(stringResource(R.string.search_tags)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onToggle) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    } else {
        IconButton(onClick = onToggle) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    }
}