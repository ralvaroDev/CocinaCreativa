package pe.ralvaro.cocinacreativa.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

/**
 * This entity contains the fields used to filter a search, we keep the id so that in a supposed
 * sharing this is used as a search format
 *
 * We use an FTS entity to declare that the table associate to this entity is a full-text search table
 * This allows us to perform fast and efficient searches in the entire table
 *
 * The [ColumnInfo] name is declared to allow flexibility for renaming the data class properties
 * without requiring changing column name
 * */
@Entity(tableName = "food_key_filters")
@Fts4
data class FoodKFtsEntity(

    /**
     * Unique string identifying
     * [PrimaryKey] is not needed due to the table type, which is for general lookup
     * and not for retrieving specific data individually
     */
    @ColumnInfo(name = "food_id") val foodId: String,

    /**
     * Food name
     */
    @ColumnInfo(name = "name") val name: String,

    /**
     * Body of text with the food's description
     */
    @ColumnInfo(name = "description") val description: String
)