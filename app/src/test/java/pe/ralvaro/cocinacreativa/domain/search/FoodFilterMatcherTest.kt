package pe.ralvaro.cocinacreativa.domain.search

import org.junit.Assert.assertTrue
import org.junit.Test
import pe.ralvaro.cocinacreativa.data.model.Filter
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.util.TestData

/**
 * Test for [FoodFilterMatcher]
 */
class FoodFilterMatcherTest {

    private fun assertMatchCase(
        userFilters: List<Filter>,
        expectedFilterList: List<FoodDish>
    ) {

        val matcher = FoodFilterMatcher(userFilters)
        val currentFilterList = TestData.foodDishList.filter {
            matcher.matches(it)
        }
        assertTrue(currentFilterList.toString(),currentFilterList == expectedFilterList)
    }

    @Test
    fun filterMatcher_filterEmpty_resultSameList() = assertMatchCase(
        userFilters = emptyList(), expectedFilterList = TestData.foodDishList
    )

    @Test
    fun filterMatcher_filterOnlyIngredients_resultSuccess() = assertMatchCase(
        userFilters = TestData.filterOnlyIngredientList, expectedFilterList = TestData.foodDishList
    )

    @Test
    fun filterMatcher_filterEntryFoodType_resultOneElement() = assertMatchCase(
        userFilters = listOf(TestData.entryFoodType),
        expectedFilterList = listOf(TestData.foodDish3)
    )

    @Test
    fun filterMatcher_filterTwoIngredientsUniqueInEach_resultTwoElements() = assertMatchCase(
        userFilters = listOf(TestData.fishIngredient, TestData.onionIngredient),
        expectedFilterList = listOf(TestData.foodDish1, TestData.foodDish2)
    )

    @Test
    fun filterMatcher_filterOneUniqueIngredientAndOneUniqueFoodTypeOfTheSame_resultOneElement() =
        assertMatchCase(
            userFilters = listOf(TestData.chickenIngredient, TestData.entryFoodType),
            expectedFilterList = listOf(TestData.foodDish3)
        )

    @Test
    fun filterMatcher_multipleFilterNoMatch_resultEmpty() =
        assertMatchCase(
            userFilters = listOf(TestData.fishIngredient, TestData.entryFoodType),
            expectedFilterList = emptyList()
        )


    // Just works of the filter is strict the others are not strict
    /*@Test
    fun filterMatcher_multipleIngredientOfOneDish_resultThis() =
        assertMatchCase(
            userFilters = listOf(TestData.lemonIngredient, TestData.chickenIngredient),
            expectedFilterList = listOf(TestData.foodDish3)
        )*/


}

