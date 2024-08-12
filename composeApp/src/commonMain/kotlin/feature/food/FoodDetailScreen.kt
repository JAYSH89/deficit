package feature.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.model.food.AmountType
import core.ui.components.button.DeficitButton
import core.ui.components.layout.DeficitHeader
import core.ui.components.modifier.noRippleClickable
import core.ui.components.textfield.DeficitTextField
import core.ui.theme.DeficitTheme
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.arrow_left
import deficit.composeapp.generated.resources.delete
import feature.food.FoodDetailViewModelEvent.SaveFood
import koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun FoodDetailScreenPreview() = DeficitTheme {
    FoodDetailScreenContent(state = FoodDetailViewModelState(), onEvent = {})
}

@Composable
fun FoodDetailScreen(foodId: Long? = null, onClickBack: () -> Unit) {
    val viewModel: FoodDetailViewModel = koinViewModel<FoodDetailViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            onClickBack()
            viewModel.onEvent(FoodDetailViewModelEvent.OnNavigate)
        }
    }

    LaunchedEffect(foodId) {
        if (foodId != null) {
            viewModel.onEvent(FoodDetailViewModelEvent.GetFood(foodId = foodId))
        }
    }

    FoodDetailScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun FoodDetailScreenContent(
    state: FoodDetailViewModelState,
    onEvent: (FoodDetailViewModelEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .noRippleClickable { focusManager.clearFocus() }
            .padding(horizontal = 8.dp),
    ) {
        IconButton(
            onClick = { onEvent(FoodDetailViewModelEvent.Back) },
            content = {
                Icon(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = null,
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                DeficitHeader(
                    title = if (state.food == null) "New food" else "Update food",
                    trailingContent = {
                        if (state.food != null) {
                            IconButton(
                                onClick = { onEvent(FoodDetailViewModelEvent.DeleteFood) },
                                content = {
                                    Icon(
                                        painter = painterResource(Res.drawable.delete),
                                        contentDescription = null,
                                    )
                                }
                            )
                        }
                    },
                )
            }

            item {
                CreateFoodForm(
                    state = state,
                    focusManager = focusManager,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun CreateFoodForm(
    modifier: Modifier = Modifier,
    state: FoodDetailViewModelState,
    focusManager: FocusManager,
    onEvent: (FoodDetailViewModelEvent) -> Unit,
) {
    var name by remember { mutableStateOf(value = "") }
    var carbs by remember { mutableStateOf(value = "") }
    var proteins by remember { mutableStateOf(value = "") }
    var fats by remember { mutableStateOf(value = "") }
    var amount by remember { mutableStateOf(value = "") }
    var selectedAmountType by remember { mutableStateOf(AmountType.PIECE) }

    LaunchedEffect(state.food) {
        if (state.food != null) {
            name = state.food.name
            carbs = state.food.carbs.toString()
            proteins = state.food.proteins.toString()
            fats = state.food.fats.toString()
            amount = state.food.amount.toString()
            selectedAmountType = state.food.amountType
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        DeficitTextField(
            label = "Name",
            value = name,
            placeholder = "Name",
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { newValue -> name = newValue },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DeficitTextField(
                modifier = Modifier.weight(1f),
                label = "Carbs",
                value = carbs,
                placeholder = "Carbs",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next,
                ),
                onValueChange = { newValue ->
                    // TODO: Fix this properly - there is no numberFormatter in Kotlin
                    carbs = newValue.replace(",", ".")
                },
            )

            Spacer(modifier = Modifier.width(12.dp))

            DeficitTextField(
                modifier = Modifier.weight(1f),
                label = "Proteins",
                value = proteins,
                placeholder = "Proteins",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next,
                ),
                onValueChange = { newValue ->
                    // TODO: Fix this properly - there is no numberFormatter in Kotlin
                    proteins = newValue.replace(",", ".")
                },
            )

            Spacer(modifier = Modifier.width(12.dp))

            DeficitTextField(
                modifier = Modifier.weight(1f),
                label = "Fats",
                value = fats,
                placeholder = "Fats",
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next,
                ),
                onValueChange = { newValue ->
                    // TODO: Fix this properly - there is no numberFormatter in Kotlin
                    fats = newValue.replace(",", ".")
                },
            )
        }

        DeficitTextField(
            label = "Amount",
            value = amount,
            placeholder = "Amount",
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            onValueChange = { newValue ->
                amount = newValue
            },
            onDone = { focusManager.clearFocus() }
        )

        Column {
            AmountType.entries.forEach { amountType ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedAmountType == amountType,
                        onClick = { selectedAmountType = amountType },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.primary,
                        )
                    )

                    Text(
                        text = amountType.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        DeficitButton(
            text = "Save",
            onClick = {
                val event = SaveFood(
                    name = name,
                    carbs = carbs,
                    proteins = proteins,
                    fats = fats,
                    amount = amount,
                    amountType = selectedAmountType,
                )

                onEvent(event)
            }
        )
    }
}
