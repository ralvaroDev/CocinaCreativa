package pe.ralvaro.cocinacreativa.data

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

}