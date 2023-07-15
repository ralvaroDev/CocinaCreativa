package pe.ralvaro.cocinacreativa.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.network.models.toFiltersTest
import pe.ralvaro.cocinacreativa.util.FakeFoodDatabase
import pe.ralvaro.cocinacreativa.util.TestBrokeDataRemoteDataSource
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.TestOkDataRemoteDataSource
import pe.ralvaro.cocinacreativa.util.TestOkLocalDataSource

/**
 * Test for [FoodDataRepository]
 */
@ExperimentalCoroutinesApi
class FoodDataRepositoryTest {

    private lateinit var repo: FoodDataRepository

    @Test
    fun obtainOfflineData_remoteResponseAreUsedOverDummyData() = runTest {

        // Give a repo with both sources OK
        repo = FoodDataRepository(
            remoteFoodDataSource = TestOkDataRemoteDataSource(),
            localFoodDataSource = TestOkLocalDataSource(),
            foodDatabase = FakeFoodDatabase()
        )

        // Request data and save data in cache
        repo.downloadFoodFromServer()

        val data = repo.getOfflineFoodData()

        // This compare to remote data, different to local data
        assertTrue(data.foodDishes.first() == TestData.foodDish1)
        assertFalse(data.foodDishes[1] == TestData.foodDish3)
    }

    @Test
    fun obtainOfflineData_remoteResponseNotWorkSoUseDummyData() = runTest {
        repo = FoodDataRepository(
            remoteFoodDataSource = TestBrokeDataRemoteDataSource(),
            localFoodDataSource = TestOkLocalDataSource(),
            foodDatabase = FakeFoodDatabase()
        )

        try {
            repo.downloadFoodFromServer()
        } catch (e: Exception) {
            assertThat(e, `is`(notNullValue()))
        }

        val data = repo.getOfflineFoodData()
        // This compare to local data
        assertTrue("Check the order of the input filters", data.foodDishes.first() == TestData.foodDish3)
        assertTrue(data.filters.contains(TestData.chickenNetIngredient.toFiltersTest()))
    }

}

