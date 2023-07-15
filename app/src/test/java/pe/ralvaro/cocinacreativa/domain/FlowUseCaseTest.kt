package pe.ralvaro.cocinacreativa.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.Result

/**
 * Test for [FlowUseCase]
 */
@ExperimentalCoroutinesApi
class FlowUseCaseTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    // Get an override dispatcher
    private val testDispatcher = coroutineRule.testDispatcher

    @Test
    fun flowUseCaseInvoke_functionException_emitsErrorType() = runTest {
        // create the instance
        val flowUseCase = ExceptionThrowerFlowUseCase(testDispatcher)
        val result = flowUseCase(Unit).first()
        assertThat(result, instanceOf(Result.Error::class.java))
    }

    class ExceptionThrowerFlowUseCase(dispatcher: CoroutineDispatcher): FlowUseCase<Unit, Unit>(dispatcher) {
        override fun execute(parameters: Unit): Flow<Result<Unit>> = flow {
            throw Exception("Test flowUseCase exception")
        }
    }

}