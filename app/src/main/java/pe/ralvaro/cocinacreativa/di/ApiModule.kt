package pe.ralvaro.cocinacreativa.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.cocinacreativa.data.network.RecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_RECIPE_URL = "https://demo7060424.mockable.io/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_RECIPE_URL)
        .build()

    @Singleton
    @Provides
    fun provideApiServices(
        retrofit: Retrofit
    ): RecipeApiService = retrofit.create(RecipeApiService::class.java)

}