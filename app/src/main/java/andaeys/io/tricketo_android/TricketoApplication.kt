package andaeys.io.tricketo_android

import andaeys.io.tricketo_android.di.appModule
import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TricketoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TricketoApplication)
            modules(appModule)
        }
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
