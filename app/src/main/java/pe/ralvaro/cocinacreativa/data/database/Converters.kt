package pe.ralvaro.cocinacreativa.data.database

import androidx.room.TypeConverter

private const val DELIMITER = ","

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString()
    }

    @TypeConverter
    fun toList(linearValue: String?): List<String>? {
        return if (linearValue.isNullOrEmpty()) emptyList() else linearValue.split(DELIMITER).map {
            it.trim()
        }
    }

}