package pe.ralvaro.cocinacreativa.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

/**
 * Test for [LocalFoodDataSource]
 */
class LocalFoodDataSourceTest {

    @Test
    fun loadAndParseDummyFoodData_loadJson_resultIsNotEmpty() {
        val data = LocalFoodDataSource.getDummyFoodData()
        Assert.assertTrue("Food Dishes data returned by Local source is empty",
            data.foodDishes.isNotEmpty()
        )
        Assert.assertTrue("Filters data returned by Local source is empty",
            data.filters.isNotEmpty()
        )
    }

    /**
     * TODO ESTOS DOS TEST SON NECESARIOS?
     */

    @Test
    fun loadAndParseDummyFoodDataInOverride_loadJson_resultIsNotEmpty() {
        val data = LocalFoodDataSource.localData()
        Assert.assertTrue("Food Dishes data returned by Local source is empty",
            data.foodDishes.isNotEmpty()
        )
        Assert.assertTrue("Filters data returned by Local source is empty",
            data.filters.isNotEmpty()
        )
    }

    @Test
    fun loadAndParseDummyFoodDataInOverrideRemote_loadJson_resultIsNull() = runTest {
        val data = LocalFoodDataSource.remoteData()
        Assert.assertTrue("Local source doesn't have to return remote data",
            data == null
        )
    }

}