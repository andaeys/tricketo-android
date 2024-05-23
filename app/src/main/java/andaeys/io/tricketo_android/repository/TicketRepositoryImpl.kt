package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.TicketConstants
import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TicketRepositoryImpl(firebaseDatabase: FirebaseDatabase) : TicketRepository {

    private val ticketsRef: DatabaseReference = firebaseDatabase.getReference(TicketConstants.TICKET_REF)
    override suspend fun getTickets(): List<TicketItem> = withContext(Dispatchers.IO) {
        val dataSnapshot = ticketsRef.get().await()
        if (dataSnapshot.childrenCount.toInt()==0){
            emptyList<TicketItem>()
        }
        dataSnapshot.children.mapNotNull {
            val ticketEntity = it.getValue(Ticket::class.java)
            TicketItem.fromTicketEntity(it.key, ticketEntity)
        }
    }

    override suspend fun addTicket(ticket: Ticket)  {
        val ticketId = ticketsRef.push().key
        ticketId?.let {
            ticketsRef.child(it).setValue(ticket).await()
        }
    }

    override suspend fun updateTicket(ticketID: String, updatedTicket: Ticket) {
        ticketsRef.child(ticketID).setValue(updatedTicket).await()
    }
}
