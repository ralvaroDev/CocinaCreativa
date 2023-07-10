package pe.ralvaro.cocinacreativa.util

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import org.mockito.Mockito
import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsDao

class FakeFoodDatabase: FoodDatabase() {
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
