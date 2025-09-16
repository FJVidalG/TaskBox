package com.fjvid.taskbox.ui.components.utils

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fjvid.taskbox.R
import com.fjvid.taskbox.data.models.Status

@Composable
fun TaskPriorityIcon(status: Status, modifier: Modifier = Modifier) {
    when (status) {
        Status.COMPLETED -> Icon(
            painter = painterResource(R.drawable.completar),
            contentDescription = stringResource(R.string.status_completed),
            tint = Color(0xFF4CAF50)
        )
        Status.PENDING -> Icon(
            painter = painterResource(R.drawable.reloj),
            contentDescription = stringResource(R.string.status_pending),
            tint = Color(0xFFFFEB3B)
        )
        Status.CANCELED -> Icon(
            painter = painterResource(R.drawable.cancelado),
            contentDescription = stringResource(R.string.status_canceled),
            tint = Color(0xFFF44336)
        )
    }
}