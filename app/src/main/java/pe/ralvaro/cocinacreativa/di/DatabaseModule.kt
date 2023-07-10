package pe.ralvaro.cocinacreativa.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.cocinacreativa.data.database.FoodDatabase
import pe.ralvaro.cocinacreativa.data.database.FoodKFtsDao
import javax.inject.Singleton

private const val NAME_FOOD_DATABASE = "food_data"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FoodDatabase {
        val builder = Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            NAME_FOOD_DATABASE
        )
            .fallbackToDestructiveMigration()
        return builder.build()
    }

}