package pe.ralvaro.cocinacreativa.ui.food_detail

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.domain.food_detail.FindSpecificFoodUseCase
import pe.ralvaro.cocinacreativa.util.FakeDefaultFoodRepository
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope

/**
 * Test for [FoodDetailViewModel]
 */
@ExperimentalCoroutinesApi
class FoodDetailViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun foodDetailViewModel_goodID_emitsSuccess() = runTest {

        val viewModel = FoodDetailViewModel(
            SavedStateHandle(initialState = mapOf("food_id" to "1a")),
            FindSpecificFoodUseCase(
                defaultFoodRepository = FakeDefaultFoodRepository(coroutineRule.coroutineScope()),
                dispatcher = coroutineRule.testDispatcher
            )
        )

        assertEquals(Result.Success(TestData.foodDish1), viewModel.foodDishData.value)
    }
    
    @Test
    fun foodDetailViewModel_wrongID_emitsError() = runTest {
        val viewModel = FoodDetailViewModel(
            SavedStateHandle(initialState = mapOf("food_id" to "111")),
            FindSpecificFoodUseCase(
                defaultFoodRepository = FakeDefaultFoodRepository(coroutineRule.coroutineScope()),
                dispatcher = coroutineRule.testDispatcher
            )
        )
        assertThat(viewModel.foodDishData.value, instanceOf(Result.Error::class.java))
    }

    @Test
    fun foodDetailViewModel_nullSaveStateValue_emitsError() = runTest {
        val viewModel = FoodDetailViewModel(
            SavedStateHandle(initialState = mapOf("food_id" to null)),
            FindSpecificFoodUseCase(
                defaultFoodRepository = FakeDefaultFoodRepository(coroutineRule.coroutineScope()),
                dispatcher = coroutineRule.testDispatcher
            )
        )
        assertThat(viewModel.foodDishData.value, instanceOf(Result.Error::class.java))
    }

}

