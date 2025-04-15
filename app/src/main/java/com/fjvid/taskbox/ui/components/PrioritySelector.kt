package com.fjvid.taskbox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrioritySelector( // funcion para la seleccion de la prioroidad con un circleshape
    currentPriority: Int,
    onPrioritySelected: (Int) -> Unit
) {
    val priorities = listOf(1, 2, 3)

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        priorities.forEach { priority ->
            val isSelected = priority == currentPriority
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onPrioritySelected(priority) }
                    .width(80.dp)
            ) {
                Icon(
                    imageVector = when (priority) {
                        3 -> Icons.Default.Warning
                        2 -> Icons.Default.Star
                        else -> Icons.Default.Info
                    },
                    contentDescription = when (priority) {
                        3 -> "Prioridad Alta"
                        2 -> "Prioridad Media"
                        else -> "Prioridad Baja"
                    },
                    tint = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .border(
                            width = 2.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary
                            else Color.Transparent,
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = when (priority) {
                        3 -> "Alta"
                        2 -> "Media"
                        else -> "Baja"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface)

            }
        }
    }
}