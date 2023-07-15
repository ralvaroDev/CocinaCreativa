package pe.ralvaro.cocinacreativa

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class MainApplication : Application(), OnMapsSdkInitializedCallback {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this);
        Timber.plant(MyTree())
    }

    override fun onMapsSdkInitialized(p0: MapsInitializer.Renderer) {
        Timber.d("Renderer Map Sdk -> ${p0.name}")
    }

}

class MyTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "$tag FATAL", message, t)
    }
}