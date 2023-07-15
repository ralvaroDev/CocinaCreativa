package pe.ralvaro.cocinacreativa.data.repository.launcher

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Storage for user preferences and certain app data.
 */
interface PreferenceDataStorage {

    suspend fun isOnboardingComplete(isComplete: Boolean)
    val isOnboardingComplete: Flow<Boolean>

}

class PreferenceDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferenceDataStorage {

    /**
     * This object stores the value of the DataStore keys
     */
    private object PreferencesKeys {
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
    }

    override suspend fun isOnboardingComplete(isComplete: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.ONBOARDING_COMPLETE] = isComplete
        }
    }
    override val isOnboardingComplete: Flow<Boolean> =
        dataStore.data.map { it[PreferencesKeys.ONBOARDING_COMPLETE] ?: false }

}