package pe.ralvaro.cocinacreativa.domain.home

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.util.DefaultSecondFakeRepo
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.coroutineScope
import pe.ralvaro.cocinacreativa.util.data

/**
 * Test for [LoadRandomRecipesUseCase]
 */
@ExperimentalCoroutinesApi
class LoadRandomRecipesUseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun loadRecipes_receiveUnorderedList_resultRecipesOrderedById() = runTest {
        val useCase = LoadRandomRecipesUseCase(
            defaultFoodRepository = DefaultSecondFakeRepo(dispatcher = coroutineRule.coroutineScope()),
            dispatcher = coroutineRule.testDispatcher
        )
        val result = useCase(Unit).first()

        // In this case last ids represents last recipes added in server
        assertThat(result.data, `is`(notNullValue()))
        assertTrue(result.data!!.recipes.isNotEmpty())

    }

}