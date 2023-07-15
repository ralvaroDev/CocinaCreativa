package pe.ralvaro.cocinacreativa.domain.launcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.ralvaro.cocinacreativa.data.repository.launcher.PreferenceDataStorage
import pe.ralvaro.cocinacreativa.di.IoDispatcher
import pe.ralvaro.cocinacreativa.domain.FlowUseCase
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.Result.Success
import javax.inject.Inject

class InitialStateUseCase @Inject constructor(
    private val preferenceRepository: PreferenceDataStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Boolean>(dispatcher) {

    override fun execute(parameters: Unit): Flow<Result<Boolean>> {
        return preferenceRepository.isOnboardingComplete.map {
            Success(it)
        }
    }

}