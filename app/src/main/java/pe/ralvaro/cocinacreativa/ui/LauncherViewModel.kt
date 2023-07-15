package pe.ralvaro.cocinacreativa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.domain.launcher.DownloadDataFromServerUseCase
import pe.ralvaro.cocinacreativa.domain.launcher.InitialStateUseCase
import pe.ralvaro.cocinacreativa.util.data
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    initialStateUseCase: InitialStateUseCase
) : ViewModel() {

    val launchDestination = initialStateUseCase(Unit).map {
        if (it.data == false)
            InitialView.Onboarding
        else
            InitialView.Main
    }

}

sealed interface InitialView {
    object Onboarding : InitialView
    object Main : InitialView
}