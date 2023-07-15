package pe.ralvaro.cocinacreativa.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Set the unconfined to provides the same scheduling behavior as runBlockingTest
 * And also provides good work in ui test eg. with viewModelScope
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule (
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

/**
 * In case we need a scope let's define one secure
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun MainCoroutineRule.coroutineScope(): CoroutineScope = TestScope(testDispatcher)