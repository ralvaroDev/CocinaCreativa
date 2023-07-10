package pe.ralvaro.cocinacreativa.data

import com.google.gson.JsonSyntaxException
import org.junit.Assert.assertTrue
import org.junit.Test

private const val TEST_DUMMY_FILE = "test_food_data.json"
private const val TEST_MALFORMED_DUMMY_FILE = "malformed_test_food_data.json"

/**
 * Test for [FoodDataJsonParser]
 */
class FoodDataJsonParserTest {

    @Test
    fun parseFoodData_readCorrectJsonAndParse_resultSuccessParsing() {
        val foodDataStream = this.javaClass.classLoader!!
            .getResource(TEST_DUMMY_FILE).openStream()

        val data = FoodDataJsonParser.parseFoodData(foodDataStream)

        // Contains elements
        assertTrue(data.foodDishes.size == 3)
        assertTrue(data.filters.size == 2)

        // Elements contained are correct by relation
        val ingredientFirstFood = data.foodDishes.first().filterTags
        val firstIngredient = data.filters.first()

        assertTrue(ingredientFirstFood.first() == firstIngredient.tag)


    }

    @Test(expected = JsonSyntaxException::class)
    fun parseFoodData_readMalformedJsonAndParse_resultThrowException() {
        val foodDataStream = this.javaClass.classLoader!!
            .getResource(TEST_MALFORMED_DUMMY_FILE).openStream()
        FoodDataJsonParser.parseFoodData(foodDataStream)
    }

}