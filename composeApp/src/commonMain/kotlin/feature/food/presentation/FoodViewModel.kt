package feature.food.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.model.food.AmountType
import core.model.food.Food
import feature.food.domain.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _state = MutableStateFlow(FoodViewModelState())
    val state: StateFlow<FoodViewModelState> = _state

    init {
        onEvent(event = FoodViewModelEvent.GetFood)
    }

    fun onEvent(event: FoodViewModelEvent) {
        when (event) {
            is FoodViewModelEvent.SaveFood -> insertFood(food = event)
            FoodViewModelEvent.GetFood -> getFood()
            FoodViewModelEvent.ToggleFoodDialog -> showAddFoodDialog()
            is FoodViewModelEvent.DeleteFood -> deleteFood(id = event.id)
        }
    }

    private fun getFood() {
        repository
            .getAllFood()
            .map { allFood -> allFood.sortedBy { it.name.lowercase() } }
            .onEach { allFood -> _state.update { it.copy(food = allFood) } }
            .launchIn(scope = viewModelScope + Dispatchers.IO)
    }

    private fun insertFood(food: FoodViewModelEvent.SaveFood) {
        val newFood = food.toFood() ?: return

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFood(food = newFood)
            _state.update { it.copy(dialogVisible = false) }
        }
    }

    private fun deleteFood(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFoodById(id = id)
        }
    }

    private fun showAddFoodDialog() {
        val dialogVisible = state.value.dialogVisible
        _state.update { it.copy(dialogVisible = !dialogVisible) }
    }
}

sealed interface FoodViewModelEvent {
    data object GetFood : FoodViewModelEvent

    data object ToggleFoodDialog : FoodViewModelEvent

    data class SaveFood(
        val name: String,
        val carbs: String,
        val proteins: String,
        val fats: String,
        val amount: String,
        val amountType: AmountType,
    ) : FoodViewModelEvent

    data class DeleteFood(val id: Long) : FoodViewModelEvent
}

fun FoodViewModelEvent.SaveFood.toFood(): Food? {
    val carbs = this.carbs.toDoubleOrNull()
    val proteins = this.proteins.toDoubleOrNull()
    val fats = this.fats.toDoubleOrNull()
    val amount = this.amount.toDoubleOrNull()

    if (carbs == null || proteins == null || fats == null || amount == null) return null

    val calories = carbs * 4 + proteins * 4 + fats * 9
    return Food(
        name = this.name,
        carbs = carbs,
        proteins = proteins,
        fats = fats,
        calories = calories,
        amount = amount,
        amountType = this.amountType,
    )
}

data class FoodViewModelState(
    val food: List<Food> = emptyList(),
    val dialogVisible: Boolean = false,
)
