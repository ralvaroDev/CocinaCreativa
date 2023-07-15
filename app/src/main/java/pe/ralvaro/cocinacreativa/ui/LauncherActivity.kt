package pe.ralvaro.cocinacreativa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.ralvaro.cocinacreativa.R
import timber.log.Timber

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val launchViewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launchViewModel.launchDestination.collect {
                    when (it) {
                        InitialView.Main -> {
                            Timber.d("Vamonos a main")
                            startActivity(
                                Intent(this@LauncherActivity, MainActivity::class.java).addFlags()
                            )
                        }
                        InitialView.Onboarding -> {
                            Timber.d("Vamonos a onboarding")
                            startActivity(
                                Intent(this@LauncherActivity, MainActivity::class.java).addFlags()
                            )
                        }
                    }
                    finish()
                }
            }
        }

    }
}

fun Intent.addFlags(): Intent {
    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    return this
}