package feature.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.ui.components.button.DeficitButton
import core.ui.components.layout.DeficitHeader
import core.ui.components.modifier.noRippleClickable
import core.ui.components.picker.DeficitDatePicker
import core.ui.components.picker.DeficitTimePicker
import core.ui.components.textfield.DeficitTextField
import core.ui.theme.DeficitTheme
import core.util.LocalDateTimeFormat
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.calendar
import deficit.composeapp.generated.resources.close
import deficit.composeapp.generated.resources.delete
import feature.journal.JournalOverviewViewModelEvent.*
import koinViewModel
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun JournalOverviewScreenPreview() = DeficitTheme {
    JournalOverviewScreenContent(state = JournalOverviewViewModelState(), onEvent = {})
}

@Composable
fun JournalOverviewScreen() {
    val viewModel: JournalOverviewViewModel = koinViewModel<JournalOverviewViewModel>()
    val state by viewModel.state.collectAsState()

    JournalOverviewScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun JournalOverviewScreenContent(
    state: JournalOverviewViewModelState,
    onEvent: (JournalOverviewViewModelEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var serving by remember { mutableStateOf(value = "") }

    DeficitDatePicker(
        visible = state.datePickerVisible,
        onConfirm = { selectedMillis -> onEvent(SetDate(selectedMillis)) },
        onDismissRequest = { onEvent(ToggleDatePicker) },
    )

    DeficitTimePicker(
        visible = state.timePickerVisible,
        hour = state.date.hour,
        minute = state.date.minute,
        onConfirm = { (hour, minute) ->
            val event = SetTime(hour = hour, minute = minute)
            onEvent(event)
        },
        onDismissRequest = { onEvent(ToggleTimePicker) },
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),
    ) {
        item {
            DeficitHeader(
                title = "Journal",
                trailingContent = {
                    IconButton(onClick = { onEvent(ToggleDatePicker) }) {
                        Icon(
                            painter = painterResource(Res.drawable.calendar),
                            contentDescription = null,
                        )
                    }
                }
            )
        }

        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.date.format(LocalDateTimeFormat.date),
                style = typography.displaySmall,
                textAlign = TextAlign.Start,
            )
        }

        item {
            if (state.selectedFood == null) {
                DeficitTextField(
                    label = "Search",
                    value = state.foodQuery,
                    placeholder = "Search food",
                    onValueChange = { query -> onEvent(FilterFood(query = query)) },
                )
            }
        }

        items(state.foods) { food ->
            if (state.selectedFood == null) {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(20.dp),
                        ),
                    onClick = { onEvent(SelectFood(food = food)) },
                    content = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = food.name,
                            style = typography.bodyMedium,
                            textAlign = TextAlign.Start,
                        )
                    }
                )
            }
        }

        item {
            if (state.selectedFood != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = state.selectedFood.name,
                            style = MaterialTheme.typography.headlineSmall,
                        )

                        IconButton(onClick = { onEvent(UnSelectFood) }) {
                            Icon(
                                painter = painterResource(resource = Res.drawable.close),
                                contentDescription = null,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        DeficitTextField(
                            modifier = Modifier.weight(1f),
                            label = "Serving",
                            value = state.serving,
                            placeholder = "Serving",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next,
                            ),
                            onValueChange = { newValue -> onEvent(SetServing(serving = newValue)) },
                            onDone = { focusManager.clearFocus() },
                            leadingContent = {
                                TextButton(onClick = { onEvent(ToggleTimePicker) }) {
                                    Text(
                                        text = state.date.format(LocalDateTimeFormat.time),
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            },
                            trailingContent = {
                                Text(
                                    modifier = Modifier
                                        .weight(weight = 1f)
                                        .padding(end = 24.dp),
                                    text = "times " +
                                            "${state.selectedFood.amount.toInt()} " +
                                            state.selectedFood.amountType.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(height = 12.dp))

                    DeficitButton(
                        text = "Save",
                        onClick = { onEvent(SaveJournalEntry) }
                    )
                }
            }
        }

        items(state.journalEntries) { journalEntry ->
            Card(
                modifier = Modifier.shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(size = 20.dp),
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = journalEntry.date.format(LocalDateTimeFormat.time),
                            style = typography.bodyMedium,
                        )

                        Column {
                            Text(
                                text = journalEntry.food.name,
                                style = typography.labelMedium,
                            )

                            Row {
                                val amount = (journalEntry.serving * journalEntry.food.amount)
                                Text(
                                    text = "${amount.toInt()} ${journalEntry.food.amountType.name}",
                                    style = typography.bodySmall,
                                )

                                Spacer(modifier = Modifier.width(width = 12.dp))

                                Text(
                                    text = "${journalEntry.calories} kcal",
                                    style = typography.bodySmall,
                                )
                            }
                        }
                    }

                    if (journalEntry.id != null) {
                        IconButton(
                            onClick = { onEvent(DeleteJournalEntry(id = journalEntry.id)) },
                            content = {
                                Icon(
                                    painter = painterResource(Res.drawable.delete),
                                    contentDescription = null,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
