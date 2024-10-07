package feature.food.presentation

import app.cash.turbine.test
import core.model.food.AmountType
import core.model.food.Food
import core.data.local.db.datasource.food.FakeFoodDataSource
import core.data.repository.FoodRepositoryImpl
import core.data.repository.FoodRepository
import feature.food.FoodOverviewViewModel
import feature.food.FoodOverviewViewModelEvent
import helpers.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FoodViewModelTest {

    private lateinit var dataSource: FakeFoodDataSource
    private lateinit var repository: FoodRepository
    private lateinit var viewModel: FoodOverviewViewModel
    private lateinit var testDispatcherProvider: TestDispatcherProvider

    private val egg = Food(
        id = 1,
        name = "Egg",
        carbs = 0.0,
        proteins = 6.2,
        fats = 4.4,
        calories = 64.0,
        amount = 1.0,
        amountType = AmountType.PIECE,
    )

    @BeforeTest
    fun setUp() {
        testDispatcherProvider = TestDispatcherProvider()
        dataSource = FakeFoodDataSource()
        repository = FoodRepositoryImpl(datasource = dataSource)
        viewModel = FoodOverviewViewModel(
            repository = repository,
            dispatcher = testDispatcherProvider,
        )
    }

    @Test
    fun `initial state`() = runTest {
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(expected = listOf(egg), actual = state.food)
            assertEquals(expected = false, actual = state.dialogVisible)
        }
    }

    @Test
    fun `Food list should be alphabetically ordered by food name`() = runTest {
        val unorderedFoods = listOf(
            egg.copy(name = "C"),
            egg.copy(name = "A"),
            egg.copy(name = "B"),
        )

        dataSource.addFood(unorderedFoods)
        advanceUntilIdle()

        viewModel.state.test {
            assertEquals(
                expected = listOf("A", "B", "C"),
                actual = awaitItem().food.map { it.name },
            )
        }
    }

    @Test
    fun `save Food should add and refresh list of foods`() = runTest {
        val event = FoodOverviewViewModelEvent.SaveFood(
            name = "Example",
            carbs = "1.0",
            proteins = "1.0",
            fats = "1.0",
            amount = "1.0",
            amountType = AmountType.PIECE,
        )

        viewModel.onEvent(event)
        advanceUntilIdle()

        val expected = Food(
            id = 2,
            name = "Example",
            carbs = 1.0,
            proteins = 1.0,
            fats = 1.0,
            calories = 17.0,
            amount = 1.0,
            amountType = AmountType.PIECE,
        )

        viewModel.state.test {
            assertEquals(expected = listOf(egg, expected), actual = awaitItem().food)
            assertTrue(cancelAndConsumeRemainingEvents().isEmpty())
        }
    }

    @Test
    fun `delete Food should remove Food from list of foods`() = runTest {
        val event = FoodOverviewViewModelEvent.DeleteFood(id = egg.id ?: 1)

        viewModel.onEvent(event)
        advanceUntilIdle()

        viewModel.state.test {
            assertEquals(expected = emptyList(), actual = awaitItem().food)
            assertTrue(cancelAndConsumeRemainingEvents().isEmpty())
        }
    }

    @Test
    fun `toggleFoodDialog should show set dialog visible`() = runTest {
        val event = FoodOverviewViewModelEvent.ToggleFoodDialog

        viewModel.onEvent(event)
        advanceUntilIdle()

        viewModel.state.test {
            assertEquals(expected = true, actual = awaitItem().dialogVisible)
            assertTrue(cancelAndConsumeRemainingEvents().isEmpty())
        }
    }
}