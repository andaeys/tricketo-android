package andaeys.io.tricketo_android.di

import andaeys.io.tricketo_android.repository.TicketRepository
import andaeys.io.tricketo_android.repository.TicketRepositoryImpl
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val appModule = module {
    single{FirebaseDatabase.getInstance()}
    single<TicketRepository>{TicketRepositoryImpl(get())}
}
