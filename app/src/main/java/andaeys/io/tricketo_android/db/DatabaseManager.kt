package andaeys.io.tricketo_android.db

import andaeys.io.tricketo_android.model.Ticket
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DatabaseManager {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ticketsRef: DatabaseReference = database.getReference("tickets")

    fun addTicket(ticket: Ticket) {
        val ticketId = ticketsRef.push().key
        ticketId?.let {
            ticketsRef.child(it).setValue(ticket)
        }
    }

    fun getTickets(onTicketsReceived: (List<Ticket>) -> Unit) {
        ticketsRef.get().addOnSuccessListener { dataSnapshot ->
            val tickets = mutableListOf<Ticket>()
            for (snapshot in dataSnapshot.children) {
                snapshot.getValue(Ticket::class.java)?.let {
                    tickets.add(it)
                }
            }
            onTicketsReceived(tickets)
        }.addOnFailureListener {
            it.stackTrace
        }


    }
}
