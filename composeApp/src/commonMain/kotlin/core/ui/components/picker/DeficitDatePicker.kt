package core.ui.components.picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import core.util.now
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeficitDatePicker(
    date: LocalDate = LocalDate.now(),
    visible: Boolean = false,
    onConfirm: (Long) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    if (visible) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = state.selectedDateMillis
                        if (selectedMillis != null)
                            onConfirm(selectedMillis)
                    },
                    content = { Text(text = "OK") }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                    content = { Text(text = "CANCEL") }
                )
            },
            content = { DatePicker(state = state) }
        )
    }
}