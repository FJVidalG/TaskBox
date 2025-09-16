package com.fjvid.taskbox.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fjvid.taskbox.data.models.Task
import com.fjvid.taskbox.R

@Composable
fun ShowDeleteTaskDialog(
    showDialog: Boolean,
    task: Task,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        DeleteDialog(
            title = stringResource(R.string.confirm_delete_title_task),
            message = stringResource(R.string.confirm_delete_message_task),
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}