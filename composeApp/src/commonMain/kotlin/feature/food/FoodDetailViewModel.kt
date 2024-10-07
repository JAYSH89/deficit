package feature.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.di.DispatcherProvider
import core.model.food.AmountType
import core.model.food.Food
import core.data.repository.FoodRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodDetailViewModel(
    private val repository: FoodRepository,
    private val dispatcher: DispatcherProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailViewModelState())
    val state: StateFlow<FoodDetailViewModelState> = _state

    private var getFoodJob: Job? = null

    fun onEvent(event: FoodDetailViewModelEvent) {
        when (event) {
            FoodDetailViewModelEvent.Back -> {
                if (!state.value.navigateBack) {
                    _state.update { it.copy(navigateBack = true) }
                }
            }

            FoodDetailViewModelEvent.OnNavigate -> {
                if (state.value.navigateBack) {
                    _state.update { it.copy(navigateBack = false) }
                }
            }

            is FoodDetailViewModelEvent.GetFood -> {
                getFood(foodId = event.foodId)
            }

            is FoodDetailViewModelEvent.SaveFood -> {
                saveFood(food = event)
            }

            is FoodDetailViewModelEvent.DeleteFood -> {
                deleteFood()
            }
        }
    }

    private fun getFood(foodId: Long) {
        getFoodJob?.cancel()
        getFoodJob = viewModelScope.launch(dispatcher.io) {
            repository
                .getFoodById(id = foodId)
                .collect { food -> _state.update { it.copy(food = food) } }
        }
    }

    private fun saveFood(food: FoodDetailViewModelEvent.SaveFood) {
        getFoodJob?.cancel()
        val newFood = food.toFood()?.copy(id = state.value.food?.id) ?: return

        viewModelScope.launch(dispatcher.io) {
            if (newFood.id == null)
                repository.insertFood(food = newFood)
            else
                repository.updateFood(food = newFood)

            _state.update { it.copy(navigateBack = true) }
        }
    }

    private fun deleteFood() {
        getFoodJob?.cancel()
        viewModelScope.launch(dispatcher.io) {
            val foodId = state.value.food?.id
            if (foodId != null) {
                repository.deleteFoodById(id = foodId)
                _state.update { it.copy(navigateBack = true) }
            }
        }
    }
}

data class FoodDetailViewModelState(
    val food: Food? = null,
    val navigateBack: Boolean = false,
)

sealed interface FoodDetailViewModelEvent {
    data object Back : FoodDetailViewModelEvent
    data object OnNavigate : FoodDetailViewModelEvent
    data class GetFood(val foodId: Long) : FoodDetailViewModelEvent
    data object DeleteFood : FoodDetailViewModelEvent
    data class SaveFood(
        val name: String,
        val carbs: String,
        val proteins: String,
        val fats: String,
        val amount: String,
        val amountType: AmountType,
    ) : FoodDetailViewModelEvent
}

fun FoodDetailViewModelEvent.SaveFood.toFood(): Food? {
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
