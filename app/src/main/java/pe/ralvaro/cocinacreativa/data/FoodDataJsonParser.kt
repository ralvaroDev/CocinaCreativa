package pe.ralvaro.cocinacreativa.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork
import java.io.InputStream

object FoodDataJsonParser {

    /**
     * Converts a .json file to a dataClass of type FoodDataNetwork.
     * @param foodDataCodified The InputStream containing the .json file to be parsed.
     * @return The parsed [FoodDataNetwork] object.
     * @throws JsonSyntaxException if there is an error while parsing the JSON.
     */
    @Throws(JsonSyntaxException::class)
    fun parseFoodData(foodDataCodified: InputStream) : FoodDataNetwork {
        val gson = GsonBuilder().create()

        val size = foodDataCodified.available()
        val buffer = ByteArray(size)
        foodDataCodified.read(buffer)
        foodDataCodified.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        return gson.fromJson(jsonString, FoodDataNetwork::class.java)
    }

}