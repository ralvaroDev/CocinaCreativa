package pe.ralvaro.cocinacreativa.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.cocinacreativa.data.FoodDataRepository
import pe.ralvaro.cocinacreativa.data.FoodDataSource
import pe.ralvaro.cocinacreativa.data.LocalFoodDataSource
import pe.ralvaro.cocinacreativa.data.RemoteFoodDataSource
import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.network.RecipeApiService
import pe.ralvaro.cocinacreativa.util.NetworkUtils
import javax.inject.Named
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    @Named("remoteDataSource")
    fun provideRemoteDataSource(
        recipeApiService: RecipeApiService,
        networkUtils: NetworkUtils
    ): FoodDataSource {
        return RemoteFoodDataSource(recipeApiService, networkUtils)
    }

    @Singleton
    @Provides
    @Named("localDataSource")
    fun provideLocalDataSource(): FoodDataSource {
        return LocalFoodDataSource
    }

    @Singleton
    @Provides
    fun provideFoodDataRepository(
        @Named("remoteDataSource") remoteFoodDataSource: FoodDataSource,
        @Named("localDataSource") localFoodDataSource: FoodDataSource,
        foodDatabase: FoodDatabase
    ): FoodDataRepository {
        return FoodDataRepository(remoteFoodDataSource, localFoodDataSource, foodDatabase)
    }

}