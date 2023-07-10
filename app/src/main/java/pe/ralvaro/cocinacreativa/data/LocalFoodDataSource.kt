package pe.ralvaro.cocinacreativa.data

import org.jetbrains.annotations.TestOnly
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork

// Temporary json name reference
private const val DUMMY_JSON_OBJECT = "food_data.json"

/**
 * Loads a dummy json from resources and parses it
 */
object LocalFoodDataSource : FoodDataSource {

    private fun loadAndParseDummyFoodData(): FoodDataNetwork {
        val foodDataStream = this.javaClass.classLoader!!
            .getResource(DUMMY_JSON_OBJECT).openStream()
        return FoodDataJsonParser.parseFoodData(foodDataStream)
    }

    @TestOnly
    fun getDummyFoodData(): FoodDataNetwork {
        return loadAndParseDummyFoodData()
    }

    override suspend fun remoteData(): FoodDataNetwork? {
        return null
    }

    override fun localData(): FoodDataNetwork {
        return loadAndParseDummyFoodData()
    }

}

