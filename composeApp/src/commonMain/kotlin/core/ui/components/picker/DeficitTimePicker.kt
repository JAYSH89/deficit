package core.ui.components.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeficitTimePicker(
    visible: Boolean = false,
    hour: Int = 0,
    minute: Int = 0,
    onConfirm: (Pair<Int, Int>) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val state = rememberTimePickerState(initialHour = hour, initialMinute = minute)

    if (visible) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Surface(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min)
                    .background(
                        shape = MaterialTheme.shapes.extraLarge,
                        color = colorScheme.inverseSurface,
                    ),
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 6.dp,
                color = colorScheme.inverseSurface,
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        text = "Select time",
                        style = MaterialTheme.typography.displayMedium,
                    )
                    TimePicker(
                        state = state,
//                        colors = TimePickerColors(
//                            clockDialColor = colorScheme.inversePrimary,
//                            selectorColor = colorScheme.inverseOnSurface,
//                            containerColor = colorScheme.inverseSurface,
//                            periodSelectorBorderColor = colorScheme.inverseOnSurface,
//                            clockDialSelectedContentColor = colorScheme.inversePrimary,
//                            clockDialUnselectedContentColor = colorScheme.inverseOnSurface,
//                            periodSelectorSelectedContainerColor = colorScheme.primaryContainer,
//                            periodSelectorUnselectedContainerColor = colorScheme.tertiary,
//                            periodSelectorSelectedContentColor = colorScheme.inversePrimary,
//                            periodSelectorUnselectedContentColor = colorScheme.inverseOnSurface,
//                            timeSelectorSelectedContainerColor = colorScheme.inversePrimary,
//                            timeSelectorUnselectedContainerColor = colorScheme.inversePrimary,
//                            timeSelectorSelectedContentColor = colorScheme.inverseOnSurface,
//                            timeSelectorUnselectedContentColor = colorScheme.inverseOnSurface,
//                        )
                    )
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = onDismissRequest) {
                            Text(text = "Cancel", color = colorScheme.inverseOnSurface)
                        }

                        TextButton(onClick = { onConfirm(state.hour to state.minute) }) {
                            Text(text = "OK", color = colorScheme.inverseOnSurface)
                        }
                    }
                }
            }
        }
    }
}