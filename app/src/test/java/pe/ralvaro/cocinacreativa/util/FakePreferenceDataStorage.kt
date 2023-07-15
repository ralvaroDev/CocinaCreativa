package pe.ralvaro.cocinacreativa.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pe.ralvaro.cocinacreativa.data.repository.launcher.PreferenceDataStorage

class FakePreferenceDataStorage(
    isOnboardingComplete: Boolean = false
) : PreferenceDataStorage {

    private val _isOnboardingComplete = MutableStateFlow(isOnboardingComplete)
    override val isOnboardingComplete: Flow<Boolean>
        get() = _isOnboardingComplete


    override suspend fun isOnboardingComplete(isComplete: Boolean) {
        _isOnboardingComplete.update { isComplete }
    }

}