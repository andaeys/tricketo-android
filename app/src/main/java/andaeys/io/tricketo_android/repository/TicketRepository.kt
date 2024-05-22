package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket

interface TicketRepository {
    suspend fun getTickets(): List<TicketItem>
    suspend fun addTicket(ticket: Ticket)
    suspend fun updateTicket(ticketID: String, updatedTicket: Ticket)
}
