package andaeys.io.tricketo_android

import andaeys.io.tricketo_android.di.domainModule
import andaeys.io.tricketo_android.di.firebaseModule
import andaeys.io.tricketo_android.di.repositoryModule
import andaeys.io.tricketo_android.di.viewModelModule
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
            modules(firebaseModule, repositoryModule, domainModule, viewModelModule)
        }
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}
