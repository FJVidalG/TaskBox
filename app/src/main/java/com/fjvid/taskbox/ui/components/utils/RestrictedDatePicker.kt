package com.fjvid.taskbox.ui.components.utils

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.util.Calendar
import com.fjvid.taskbox.R

@ExperimentalMaterial3Api
@Composable
fun RestrictedDatePicker(
    currentDate: Long,
    initialDate: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = maxOf(initialDate, currentDate),
        selectableDates = CustomSelectableDates(currentDate) // Fecha actual, instancia de mi clase CustomSelectableDates
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                datePickerState.selectedDateMillis?.let { // Contiene la fecha seleccionada por el usuario en milisegundos
                    onDateSelected(maxOf(it, currentDate)) // Permite seleccionar solo fechas posteriores a la fecha actual obtenida del datePickerState
                }
                onDismiss()
            }) { Text(stringResource(R.string.confirm)) }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text(stringResource(R.string.dismiss)) }
        }
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

/**
 * Clase que implementa la interfaz SelectableDates
 * que permite seleccionar solo fechas concretas para el DataPicker
 */
@ExperimentalMaterial3Api
class CustomSelectableDates(private val currentDate: Long) : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean { // Permite seleccionar solo fechas posteriores a la fecha actual
        return utcTimeMillis >= currentDate
    }

    override fun isSelectableYear(year: Int): Boolean { // Permite seleccionar solo años posteriores al año actual
        return year >= Calendar.getInstance().get(Calendar.YEAR)
    }
}