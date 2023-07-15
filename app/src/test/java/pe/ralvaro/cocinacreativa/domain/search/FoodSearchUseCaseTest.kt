package pe.ralvaro.cocinacreativa.domain.search

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDataRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDatabase
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope
import pe.ralvaro.cocinacreativa.util.data
import pe.ralvaro.cocinacreativa.util.successOr

/**
 * Test for [FoodSearchUseCase]
 */
@ExperimentalCoroutinesApi
class FoodSearchUseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var defaultFoodRepository: DefaultFoodRepository

    @Before
    fun init() {
        defaultFoodRepository = DefaultFoodRepository(
            foodDataRepository = FakeFoodDataRepository(coroutineRule.coroutineScope()),
            foodDatabase = FakeFoodDatabase()
        )
    }

    // This case is impossible because of one filter in viewModel
    @Test
    fun foodSearchUseCase_noFilterSelectedAndEmptyQuery_returnsAllDataBecauseOfInclusive() = runTest {
        val searchUseCase = FoodSearchUseCase(
            defaultFoodRepository = defaultFoodRepository,
            dispatcher = coroutineRule.testDispatcher
        )
        val result = searchUseCase(FoodSearchUseCaseParams(query = "", filters = emptyList())).first().successOr(
            emptyList()
        )
        assertTrue(result == TestData.foodDataRemoteOKToFoodDish.foodDishes)

    }

    @Test
    fun foodSearchUseCase_selectedFilterAndEmptyQuery_returnsSecondFoodAccordToData() = runTest {
        val searchUseCase = FoodSearchUseCase(
            defaultFoodRepository = defaultFoodRepository,
            dispatcher = coroutineRule.testDispatcher
        )
        val result = searchUseCase(FoodSearchUseCaseParams(query = "", filters = listOf(TestData.beefIngredient))).first()
        assertTrue(result.data.toString(),result.data == listOf(TestData.foodDish2))
    }

    /**
     * TODO - REMAINING TEST
     * Falta caso de si va con query y ningun filtro cumple
     * Entra query y filtra solo uno
     * Entra query y no encuentra ninguno
     */

}