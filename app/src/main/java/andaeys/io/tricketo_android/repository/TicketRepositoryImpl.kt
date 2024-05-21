package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.Ticket
import andaeys.io.tricketo_android.model.TicketConstants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class TicketRepositoryImpl(firebaseDatabase: FirebaseDatabase) : TicketRepository {

    private val ticketsRef: DatabaseReference = firebaseDatabase.getReference(TicketConstants.TICKET_REF)
    override suspend fun getTickets(): List<Ticket> {
        val dataSnapshot = ticketsRef.get().await()
        return dataSnapshot.children.mapNotNull {
            it.getValue(Ticket::class.java)
        }
    }

    override suspend fun addTicket(ticket: Ticket) {
        val ticketId = ticketsRef.push().key
        ticketId?.let {
            ticketsRef.child(it).setValue(ticket).await()
        }
    }

    override suspend fun updateTicket(ticketID: String, updatedTicket: Ticket) {
        ticketsRef.child(ticketID).setValue(updatedTicket).await()
    }
}
