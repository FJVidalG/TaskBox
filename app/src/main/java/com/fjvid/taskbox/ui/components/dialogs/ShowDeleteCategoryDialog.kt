package com.fjvid.taskbox.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Category

@Composable
fun ShowDeleteCategoryDialog(
    showDialog: Boolean,
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        DeleteDialog(
            title = stringResource(R.string.confirm_delete_title_category),
            message = stringResource(R.string.confirm_delete_message_category),
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}