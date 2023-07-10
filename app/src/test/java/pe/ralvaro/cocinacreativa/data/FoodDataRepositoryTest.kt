package pe.ralvaro.cocinacreativa.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.network.models.FiltersNetwork
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import pe.ralvaro.cocinacreativa.data.network.models.FoodDishesNetwork
import pe.ralvaro.cocinacreativa.data.network.models.toFiltersTest
import pe.ralvaro.cocinacreativa.data.network.models.toFoodDishesTest
import pe.ralvaro.cocinacreativa.util.FakeFoodDatabase

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
        assertTrue(data.foodDishes.first() == TestSimpleData.dishNet1.toFoodDishesTest())
        assertFalse(data.foodDishes.first() == TestSimpleData.dishNet3.toFoodDishesTest())
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
        assertTrue(data.foodDishes.first() == TestSimpleData.dishNet3.toFoodDishesTest())
        assertTrue(data.filters.first() == TestSimpleData.ingredient4.toFiltersTest())
    }

}

/*
* Test data and classes
* */

class TestBrokeDataRemoteDataSource: FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork? {
        throw Exception("404")
    }

    override fun localData(): FoodDataNetwork? {
        return null
    }

}

class TestOkDataRemoteDataSource : FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork {
        return FoodDataNetwork(
            foodDishes = listOf(
                TestSimpleData.dishNet1,
                TestSimpleData.dishNet2
            ), filters = listOf(
                TestSimpleData.ingredient1,
                TestSimpleData.ingredient2,
                TestSimpleData.ingredient3
            )
        )
    }

    override fun localData(): FoodDataNetwork? {
        return null
    }
}

class TestOkLocalDataSource : FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork? {
        return null
    }

    override fun localData(): FoodDataNetwork {
        return FoodDataNetwork(
            foodDishes = listOf(
                TestSimpleData.dishNet3
            ),
            filters = listOf(
                TestSimpleData.ingredient4,
                TestSimpleData.ingredient5,
                TestSimpleData.ingredient6
            )
        )
    }
}

object TestSimpleData {

    val dishNet1 = FoodDishesNetwork(
        id = "1a",
        foodName = "Ceviche",
        imageUrl = "www",
        coordinates = "-12.086585,-77.046739",
        description = "Este plato es a base de pescado, originario de las zonas de la costa peruana...",
        filterTags = listOf(
            "ingredient_fish",
            "type_dish_fondo",
            "ingredient_lemon",
            "ingredient_salt"
        )
    )

    val dishNet2 = FoodDishesNetwork(
        id = "2b",
        foodName = "Lomo Saltado",
        imageUrl = "www",
        coordinates = "-12.092336,-77.031019",
        description = "Este plato combina la carne de res con cebolla, tomate y papas fritas...",
        filterTags = listOf(
            "ingredient_beef",
            "type_dish_fondo",
            "ingredient_onion",
            "ingredient_vinegar"
        )
    )

    val dishNet3 = FoodDishesNetwork(
        id = "3c",
        foodName = "Pollo Frito",
        imageUrl = "www.image.com",
        coordinates = "-12.0453566,-77.031019",
        description = "Plato a base de pollo, arroz y papas",
        filterTags = listOf(
            "ingredient_chicken",
            "type_dish_fondo",
            "ingredient_rice",
            "ingredient_potatoes"
        )
    )

    val ingredient1 = FiltersNetwork(1, "Pescado", "ingredient", "ingredient_fish", "#4768FD")

    val ingredient2 = FiltersNetwork(2, "Limon", "ingredient", "ingredient_lemon", "#A8D83D")

    val ingredient3 = FiltersNetwork(3, "Sal", "ingredient", "ingredient_salt", "#F2891D")

    val ingredient4 = FiltersNetwork(4, "Rocoto", "ingredient", "ingredient_hot_pepper", "#E13939")

    val ingredient5 = FiltersNetwork(5, "Cebolla", "ingredient", "ingredient_onion", "#FD7C47")

    val ingredient6 = FiltersNetwork(6, "Cilantro", "ingredient", "ingredient_herb", "#48A7F2")

    val ingredientList = listOf(
        ingredient1,
        ingredient2,
        ingredient3,
        ingredient4,
        ingredient5,
        ingredient6
    )
}