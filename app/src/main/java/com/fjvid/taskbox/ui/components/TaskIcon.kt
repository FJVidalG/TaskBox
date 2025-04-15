package com.fjvid.taskbox.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.fjvid.taskbox.R

@Composable
fun TaskIcon(isCompleted: Boolean) { // funcion para seleccionar un icono default o el descargado de internet
    if (isCompleted) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Completada",
            tint = Color.Green
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.checkbox_blank_circle_outline),
            contentDescription = "Pendiente",
            tint = Color.Gray
        )
    }
}