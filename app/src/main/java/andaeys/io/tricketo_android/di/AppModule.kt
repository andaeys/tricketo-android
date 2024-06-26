package andaeys.io.tricketo_android.di

import andaeys.io.tricketo_android.doamin.AddTicket
import andaeys.io.tricketo_android.doamin.AddTicketImpl
import andaeys.io.tricketo_android.doamin.GetTicketList
import andaeys.io.tricketo_android.doamin.GetTicketListImpl
import andaeys.io.tricketo_android.doamin.SortTicketByAttribute
import andaeys.io.tricketo_android.doamin.SortTicketByAttributeImpl
import andaeys.io.tricketo_android.doamin.UpdateTicket
import andaeys.io.tricketo_android.doamin.UpdateTicketImpl
import andaeys.io.tricketo_android.repository.TicketRepository
import andaeys.io.tricketo_android.repository.TicketRepositoryImpl
import andaeys.io.tricketo_android.viewmodels.TicketEditViewModel
import andaeys.io.tricketo_android.viewmodels.TicketLIstViewModel
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val firebaseModule = module {
    single{FirebaseDatabase.getInstance()}
}

val repositoryModule = module {
    single<TicketRepository>{TicketRepositoryImpl(get())}
}

val domainModule = module {
    single<GetTicketList>{GetTicketListImpl(get())}
    single<AddTicket>{AddTicketImpl(get())}
    single<UpdateTicket>{UpdateTicketImpl(get())}
    single<SortTicketByAttribute>{SortTicketByAttributeImpl()}
}

val viewModelModule = module {
    factory { TicketLIstViewModel(get(), get()) }
    factory { TicketEditViewModel(get(), get()) }
}
