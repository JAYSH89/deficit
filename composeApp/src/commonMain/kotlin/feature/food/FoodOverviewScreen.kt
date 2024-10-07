package feature.food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import core.model.food.Food
import core.ui.components.layout.DeficitHeader
import core.ui.components.modifier.noRippleClickable
import core.ui.components.text.DualStyledText
import core.ui.components.textfield.DeficitTextField
import core.ui.theme.DeficitTheme
import deficit.composeapp.generated.resources.Res
import deficit.composeapp.generated.resources.plus
import feature.food.FoodOverviewViewModelEvent.*
import koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Preview
@Composable
private fun FoodScreenPreview() = DeficitTheme {
    FoodOverviewScreenContent(state = FoodOverviewViewModelState(), onEvent = {})
}

@Composable
fun FoodOverviewScreen(onSelectFood: (Long?) -> Unit) {
    val viewModel: FoodOverviewViewModel = koinViewModel<FoodOverviewViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.selectedFoodId) {
        if (state.selectedFoodId != null) {
            val id = state.selectedFoodId ?: return@LaunchedEffect
            onSelectFood(id)
            viewModel.onEvent(OnNavigate)
        }
    }

    LaunchedEffect(state.createFood) {
        if (state.createFood) {
            onSelectFood(null)
            viewModel.onEvent(OnNavigate)
        }
    }

    FoodOverviewScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun FoodOverviewScreenContent(
    state: FoodOverviewViewModelState,
    onEvent: (FoodOverviewViewModelEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable { focusManager.clearFocus() }
            .padding(horizontal = 20.dp),
    ) {
        DeficitHeader(
            title = "Food",
            trailingContent = {
                IconButton(
                    onClick = {
                        onEvent(CreateFood)
                    },
                    content = {
                        Icon(
                            painter = painterResource(Res.drawable.plus),
                            contentDescription = null,
                        )
                    }
                )
            },
        )

        DeficitTextField(
            label = "Search",
            value = state.foodQuery,
            placeholder = "Search food",
            onDone = { focusManager.clearFocus() },
            onValueChange = { query ->
                val event = FoodOverviewViewModelEvent.FilterFood(query = query)
                onEvent(event)
            }
        )

        Spacer(modifier = Modifier.height(height = 8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            content = {
                items(state.food) { food ->
                    FoodRow(food = food, onEvent = onEvent)
                }
            }
        )
    }
}

@Composable
private fun FoodRow(
    modifier: Modifier = Modifier,
    food: Food,
    onEvent: (FoodOverviewViewModelEvent) -> Unit,
) = Card(
    modifier = modifier,
    shape = RoundedCornerShape(size = 32.dp),
    elevation = CardDefaults.cardElevation(4.dp),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (food.id != null) {
                    onEvent(SelectFood(id = food.id))
                }
            }
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(text = food.name, style = MaterialTheme.typography.headlineSmall)

        DualStyledText(
            leadingText = "${food.amount.roundToInt()} ${food.amountType.name}",
            leadingTextStyle = MaterialTheme.typography.bodyMedium,
            trailingText = "(Kcal: ${food.calories})",
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DualStyledText(
                leadingText = "Carbs:",
                leadingTextStyle = MaterialTheme.typography.labelSmall,
                trailingText = "${food.carbs}",
            )

            DualStyledText(
                leadingText = "Proteins:",
                leadingTextStyle = MaterialTheme.typography.labelSmall,
                trailingText = "${food.proteins}",
            )

            DualStyledText(
                leadingText = "Fats:",
                leadingTextStyle = MaterialTheme.typography.labelSmall,
                trailingText = "${food.fats}",
            )
        }
    }
}
