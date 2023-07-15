package pe.ralvaro.cocinacreativa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The [Room] database for this app used as a search mechanism
 */
@Database(
    entities = [
        FoodKFtsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodKFtsDao(): FoodKFtsDao

}