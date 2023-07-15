package pe.ralvaro.cocinacreativa.ui.search

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.data.repository.food.DefaultFoodRepository
import pe.ralvaro.cocinacreativa.domain.search.FoodSearchUseCase
import pe.ralvaro.cocinacreativa.domain.search.LoadSearchFiltersUseCase
import pe.ralvaro.cocinacreativa.util.FakeDefaultFoodRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDataRepository
import pe.ralvaro.cocinacreativa.util.FakeSearchFoodDatabase
import pe.ralvaro.cocinacreativa.util.FakeTagRepository
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope

/**
 * Test for [SearchViewModel]
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var loadFiltersUseCase: LoadSearchFiltersUseCase

    @Before
    fun init() {
        loadFiltersUseCase = LoadSearchFiltersUseCase(
            tagRepository = FakeTagRepository(coroutineRule.coroutineScope()),
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun searchViewModel_filterStateChangeAndEmitsCorrectly() = runTest {
        val foodSearchUseCase = FoodSearchUseCase(
            defaultFoodRepository = mock { },
            coroutineRule.testDispatcher
        )
        val viewModel = SearchViewModel(
            loadSearchFiltersUseCase = loadFiltersUseCase,
            foodSearchUseCase = foodSearchUseCase
        )
        assertTrue(viewModel.filterChips.value.isNotEmpty())
    }

    @Test
    fun searchViewModel_filterSelectionAndThenCleanIt() = runTest {
        val foodSearchUseCase = FoodSearchUseCase(
            defaultFoodRepository = FakeDefaultFoodRepository(coroutineRule.coroutineScope()),
            coroutineRule.testDispatcher
        )
        val viewModel = SearchViewModel(
            loadSearchFiltersUseCase = loadFiltersUseCase,
            foodSearchUseCase = foodSearchUseCase
        )
        // First search results are empty
        assertEquals(emptyList<FoodDish>(), viewModel.searchResults.value)

        // Select a filter
        viewModel.updateCheckedTags(listOf("ingredient_fish"))
        advanceUntilIdle()

        // Filters should be updated and also first dish should be emitted
        assertEquals(TestData.fishIngredient,viewModel.selectedFilters.first())
        assertEquals(listOf(TestData.foodDish1), viewModel.searchResults.value)

        // Clean filter selection
        viewModel.clearFilters()
        advanceUntilIdle()

        // Filters and search result should be clean
        assertEquals(emptyList<Filter>(),viewModel.selectedFilters)
        assertEquals(emptyList<FoodDish>(), viewModel.searchResults.value)

    }

    @Test
    fun searchViewModel_searchByQuery() = runTest {
        val foodSearchUseCase = FoodSearchUseCase(
            defaultFoodRepository = DefaultFoodRepository(
                foodDataRepository = FakeFoodDataRepository(coroutineRule.coroutineScope()),
                foodDatabase = FakeSearchFoodDatabase()
            ),
            coroutineRule.testDispatcher
        )
        val viewModel = SearchViewModel(
            loadSearchFiltersUseCase = loadFiltersUseCase,
            foodSearchUseCase = foodSearchUseCase
        )

        // Insert query
        viewModel.onSearchQueryChanged(FakeSearchFoodDatabase.QUERY_FOOD_NAME)
        advanceUntilIdle()

        assertEquals(listOf(TestData.foodDish1), viewModel.searchResults.first())

        // Clean results
        viewModel.onSearchQueryChanged("")
        advanceUntilIdle()
        assertEquals(emptyList<FoodDish>(), viewModel.searchResults.value)
    }

}