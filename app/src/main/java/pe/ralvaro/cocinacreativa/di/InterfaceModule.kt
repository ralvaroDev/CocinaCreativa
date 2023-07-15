package pe.ralvaro.cocinacreativa.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.ralvaro.cocinacreativa.data.repository.launcher.PreferenceDataStorage
import pe.ralvaro.cocinacreativa.data.repository.launcher.PreferenceDataStorageImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindPreferenceDataStorage(
        preferenceDataStorageImpl: PreferenceDataStorageImpl
    ): PreferenceDataStorage

}