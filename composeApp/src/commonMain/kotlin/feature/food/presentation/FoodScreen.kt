package feature.food.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.model.food.AmountType
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.delete
import deficit.composeapp.generated.resources.plus
import koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun FoodScreenPreview() {
    FoodScreenContent(
        state = FoodViewModelState(),
        onEvent = {},
    )
}

@Composable
fun FoodScreen() {
    val viewModel: FoodViewModel = koinViewModel<FoodViewModel>()
    val state by viewModel.state.collectAsState()

    FoodScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun FoodScreenContent(
    state: FoodViewModelState,
    onEvent: (FoodViewModelEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Header(
                onClick = { onEvent(FoodViewModelEvent.ToggleFoodDialog) },
            )
        }

        items(state.food) { food ->
            FoodRow(
                id = food.id ?: 0,
                name = food.name,
                carbs = food.carbs.toString(),
                proteins = food.proteins.toString(),
                fats = food.fats.toString(),
                calories = food.calories.toString(),
                onEvent = onEvent,
            )
        }
    }

    if (state.dialogVisible) CreateFoodForm(state = state, onEvent = onEvent)
}

@Composable
private fun Header(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Food",
            style = MaterialTheme.typography.h4,
        )

        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(Res.drawable.plus),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun CreateFoodForm(
    modifier: Modifier = Modifier,
    state: FoodViewModelState,
    onEvent: (FoodViewModelEvent) -> Unit,
) {
    var name by remember { mutableStateOf(value = "") }
    var carbs by remember { mutableStateOf(value = "") }
    var proteins by remember { mutableStateOf(value = "") }
    var fats by remember { mutableStateOf(value = "") }
    var amount by remember { mutableStateOf(value = "") }
    var selectedAmountType by remember { mutableStateOf(AmountType.PIECE) }

    LaunchedEffect(state.dialogVisible) {
        if (!state.dialogVisible) {
            name = ""
            carbs = ""
            proteins = ""
            fats = ""
            amount = ""
            selectedAmountType = AmountType.PIECE
        }
    }

    Dialog(onDismissRequest = { onEvent(FoodViewModelEvent.ToggleFoodDialog) }) {
        Card {
            Column(
                modifier = modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(text = "New food", style = MaterialTheme.typography.h4)

                TextField(
                    value = name,
                    placeholder = { Text(text = "Name") },
                    onValueChange = { newValue -> name = newValue },
                )

                TextField(
                    value = carbs,
                    placeholder = { Text(text = "Carbs") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { newValue -> carbs = newValue },
                )

                TextField(
                    value = proteins,
                    placeholder = { Text(text = "Proteins") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { newValue -> proteins = newValue },
                )

                TextField(
                    value = fats,
                    placeholder = { Text(text = "Fats") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { newValue -> fats = newValue },
                )

                TextField(
                    value = amount,
                    placeholder = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    onValueChange = { newValue -> amount = newValue },
                )

                AmountType.entries.forEach { amountType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        RadioButton(
                            selected = selectedAmountType == amountType,
                            onClick = { selectedAmountType = amountType },
                        )

                        Text(text = amountType.name)
                    }
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onEvent(
                            FoodViewModelEvent.SaveFood(
                                name = name,
                                carbs = carbs,
                                proteins = proteins,
                                fats = fats,
                                amount = amount,
                                amountType = selectedAmountType,
                            ),
                        )
                    },
                    content = { Text(modifier = Modifier.padding(all = 8.dp), text = "Save") },
                )
            }
        }
    }
}

@Composable
private fun FoodRow(
    modifier: Modifier = Modifier,
    id: Long,
    name: String,
    carbs: String,
    proteins: String,
    fats: String,
    calories: String,
    onEvent: (FoodViewModelEvent) -> Unit,
) {
    Card {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "$name ($id)", style = MaterialTheme.typography.body1)

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Carbs: $carbs",
                    style = MaterialTheme.typography.caption,
                )

                Text(
                    text = "Proteins: $proteins",
                    style = MaterialTheme.typography.caption,
                )

                Text(
                    text = "Fats: $fats",
                    style = MaterialTheme.typography.caption,
                )

                Text(
                    text = "Kcal: $calories",
                    style = MaterialTheme.typography.caption,
                )
            }

            IconButton(
                onClick = { onEvent(FoodViewModelEvent.DeleteFood(id = id)) },
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
