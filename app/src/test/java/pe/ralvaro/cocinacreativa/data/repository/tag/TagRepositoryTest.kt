package pe.ralvaro.cocinacreativa.data.repository.tag

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.ralvaro.cocinacreativa.util.FakeFoodDataRepository
import pe.ralvaro.cocinacreativa.util.MainCoroutineRule
import pe.ralvaro.cocinacreativa.util.TestData
import pe.ralvaro.cocinacreativa.util.coroutineScope

/**
 * Test for [TagRepository]
 */
@ExperimentalCoroutinesApi
class TagRepositoryTest {

    // Let's override the main dispatcher
    @get:Rule
    var coroutineRule = MainCoroutineRule()


    private lateinit var dataRepository: FakeFoodDataRepository

    // Let's use the remote data - if we omit these lines, the local data is used the test will fail
    // Here we download the data first
    @Before
    fun init() {
        dataRepository = FakeFoodDataRepository(coroutineRule.coroutineScope())
    }

    @Test
    fun getFilters_remoteOk_returnListFromRemote() = runTest {
        val repo = TagRepository(dataRepository)
        val filters = repo.getFilters()
        assertTrue(filters == TestData.foodDataRemoteOKToFoodDish.filters)
    }

}