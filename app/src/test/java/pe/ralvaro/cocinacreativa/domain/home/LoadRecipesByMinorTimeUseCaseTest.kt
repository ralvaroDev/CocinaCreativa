package pe.ralvaro.cocinacreativa.domain.home

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.util.DefaultSecondFakeRepo
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope
import pe.ralvaro.cocinacreativa.util.data

/**
 * Test for [LoadRecipesByMinorTimeUseCase]
 */
@ExperimentalCoroutinesApi
class LoadRecipesByMinorTimeUseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun loadRecipes_receiveUnorderedList_resultRecipesOrderedById() = runTest {
        val useCase = LoadRecipesByMinorTimeUseCase(
            defaultFoodRepository = DefaultSecondFakeRepo(dispatcher = coroutineRule.coroutineScope()),
            dispatcher = coroutineRule.testDispatcher
        )
        useCase.isTest = true
        val result = useCase(Unit).first()

        // In this case last ids represents last recipes added in server
        assertEquals(result.data?.toString(), TestData.foodDish1.id, result.data?.recipes?.first()?.id)
        assertEquals(TestData.foodDish3.id, result.data?.recipes?.get(2)?.id)
    }

}