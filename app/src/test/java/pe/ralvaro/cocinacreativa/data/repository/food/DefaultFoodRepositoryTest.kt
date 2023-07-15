package pe.ralvaro.cocinacreativa.data.repository.food

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.util.FakeFoodDataRepository
import pe.ralvaro.cocinacreativa.util.FakeFoodDatabase
import pe.ralvaro.cocinacreativa.util.FakeSearchFoodDatabase
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope

/**
 * Test for [DefaultFoodRepository]
 */
@ExperimentalCoroutinesApi
class DefaultFoodRepositoryTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var defaultRepo: DefaultFoodRepository
    private val searchRepo: DefaultFoodRepository by lazy {
        DefaultFoodRepository(
            foodDataRepository = dataRepository,
            foodDatabase = FakeSearchFoodDatabase()
        )
    }
    private lateinit var dataRepository: FakeFoodDataRepository

    // Let's use the remote data - if we omit these lines, the local data is used the test will fail
    // Here we download the data first
    @Before
    fun init() {
        dataRepository = FakeFoodDataRepository(coroutineRule.coroutineScope())
    }

    @Test
    fun getFoods_resultAllFoods() = runTest {
        defaultRepo = DefaultFoodRepository(
            foodDataRepository = dataRepository,
            foodDatabase = FakeFoodDatabase()
        )
        // get only the foods with no filters
        val allFoods = defaultRepo.getFoods()

        assertTrue(allFoods.first().contains(TestData.foodDish1))
        assertFalse(allFoods.first().contains(TestData.foodDish3))

    }

    @Test
    fun filterFoodsByQuery_emptyQuery_resultFullList() = expectedResultFromRoom(
        "",
        TestData.foodDataRemoteOKToFoodDish.foodDishes
    )

    @Test
    fun filterFoodsByQuery_someString_resultFilterList() = expectedResultFromRoom(
        FakeSearchFoodDatabase.QUERY_FOOD_NAME,
        listOf(TestData.foodDish1)
    )

    @Test
    fun filterFoodsByQuery_onlySpacesString_resultEmptyList() = expectedResultFromRoom(
        FakeSearchFoodDatabase.QUERY_ONLY_SPACES,
        emptyList()
    )

    @Test
    fun filterFoodsByQuery_weirdStringWithNoMatches_resultEmptyList() = expectedResultFromRoom(
        FakeSearchFoodDatabase.QUERY_WITH_NO_MATCH,
        emptyList()
    )

    private fun expectedResultFromRoom(
        query: String,
        expectedListFiltered: List<FoodDish>
    ) = runTest {
        val filterList = searchRepo.filterFoodsByQuery(query)
        assertTrue(filterList == expectedListFiltered)
    }

}

