package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.Ticket
import andaeys.io.tricketo_android.model.TicketConstants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.coroutines.tasks.await

class TicketRepositoryImpl(firebaseDatabase: FirebaseDatabase) : TicketRepository {

    private val ticketsRef: DatabaseReference = firebaseDatabase.getReference(TicketConstants.TICKET_REF)
    override suspend fun getTickets(): List<Ticket> {
        return try {
            val dataSnapshot = ticketsRef.get().await()
            val tickets = mutableListOf<Ticket>()

            for (snapshot in dataSnapshot.children) {
                snapshot.getValue(Ticket::class.java)?.let {
                    tickets.add(it)
                }
            }
            tickets
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addTicket(ticket: Ticket) {
        val ticketId = ticketsRef.push().key
        ticketId?.let {
            ticketsRef.child(it).setValue(ticket).await()
        }
    }

    override suspend fun updateTicket(updatedTicket: Ticket) {
        val query: Query = ticketsRef.orderByChild("ticketID").equalTo(updatedTicket.ticketID)
        val snapshot = query.get().await()
        if (snapshot.exists()) {
            for (ticketSnapshot in snapshot.children) {
                ticketSnapshot.ref.setValue(updatedTicket).await()
            }
        }
    }
}
