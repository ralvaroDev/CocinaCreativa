package pe.ralvaro.cocinacreativa.util

import pe.ralvaro.cocinacreativa.data.FoodDataSource
import pe.ralvaro.cocinacreativa.data.network.models.FoodDataNetwork

/*
* Test data and classes
* */

class TestBrokeDataRemoteDataSource: FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork? {
        throw Exception("404")
    }

    override fun localData(): FoodDataNetwork? {
        return null
    }

}

class TestOkLocalDataSource : FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork? {
        return null
    }

    override fun localData(): FoodDataNetwork {
        return TestData.foodDataLocalOK
    }
}

class TestOkDataRemoteDataSource : FoodDataSource {
    override suspend fun remoteData(): FoodDataNetwork {
        return TestData.foodDataRemoteOK
    }

    override fun localData(): FoodDataNetwork? {
        return null
    }
}