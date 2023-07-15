package pe.ralvaro.cocinacreativa.util

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import org.mockito.Mockito
import org.mockito.kotlin.mock
import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsDao
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsEntity

class FakeFoodDatabase : FoodDatabase() {
    override fun foodKFtsDao(): FoodKFtsDao {
        return Mockito.mock(FoodKFtsDao::class.java)
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        return Mockito.mock(SupportSQLiteOpenHelper::class.java)
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return Mockito.mock(InvalidationTracker::class.java)
    }

    override fun clearAllTables() {}
}

class FakeSearchFoodDatabase : FoodDatabase() {
    // Just implement what we will gonna use
    override fun foodKFtsDao(): FoodKFtsDao {
        return object : FoodKFtsDao {
            override fun insertAll(foodKFtsEntity: List<FoodKFtsEntity>) {}

            override suspend fun getAllFoodDishes(query: String): List<String> {
                return when (query) {
                    QUERY_FOOD_NAME_IN -> listOf(TestData.foodDish1.id)
                    QUERY_ONLY_SPACES_IN -> emptyList()
                    QUERY_WITH_NO_MATCH_IN -> emptyList()
                    else -> emptyList()
                }
            }

        }
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return mock {}
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    companion object {
        const val QUERY_FOOD_NAME = "Ceviche"
        const val QUERY_WITH_NO_MATCH = "oooowwwwqq fffs"
        const val QUERY_ONLY_SPACES = "  "

        private const val QUERY_FOOD_NAME_IN = "Ceviche*"
        private const val QUERY_WITH_NO_MATCH_IN = "oooowwwwqq fffs*"
        private const val QUERY_ONLY_SPACES_IN = "  *"
    }

}
