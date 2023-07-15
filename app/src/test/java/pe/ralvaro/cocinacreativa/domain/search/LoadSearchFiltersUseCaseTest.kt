package pe.ralvaro.cocinacreativa.domain.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.repository.tag.TagRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDataRepository
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope
import pe.ralvaro.cocinacreativa.util.successOr

/**
 * Test for [LoadSearchFiltersUseCase]
 */
@ExperimentalCoroutinesApi
class LoadSearchFiltersUseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()


    @Test
    fun loadFiltersUseCase_resultOrderedFilters() = runTest {
        val useCase = LoadSearchFiltersUseCase(
            TagRepository(FakeFoodDataRepository(coroutineRule.coroutineScope())),
            coroutineRule.testDispatcher
        )
        val filtersActualOrder = useCase(Unit).successOr(emptyList())
        val ingredientsRemoteOKOrdered = listOf(
            TestData.fishIngredient,
            TestData.lemonIngredient,
            TestData.beefIngredient,
            TestData.onionIngredient,
            TestData.bottomFoodType
        )
        assertTrue(filtersActualOrder == ingredientsRemoteOKOrdered)
    }



}