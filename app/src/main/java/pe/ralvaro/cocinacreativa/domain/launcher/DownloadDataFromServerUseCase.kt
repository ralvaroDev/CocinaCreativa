package pe.ralvaro.cocinacreativa.domain.launcher

import kotlinx.coroutines.CoroutineDispatcher
import pe.ralvaro.cocinacreativa.data.FoodDataRepository
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.UseCase
import timber.log.Timber
import javax.inject.Inject

class DownloadDataFromServerUseCase @Inject constructor(
    private val foodDataRepository: FoodDataRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(parameters: Unit) {
        try {
            foodDataRepository.downloadFoodFromServer()
            Timber.d("Success download")

        } catch (e: Exception) {
            Timber.e(e,"Remote data download failed")
            // The use case will catch this error
            throw e
        }
    }
}