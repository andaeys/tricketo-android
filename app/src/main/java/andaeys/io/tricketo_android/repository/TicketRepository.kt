package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.entity.Ticket

interface TicketRepository {
    suspend fun getTickets(): List<Ticket>
    suspend fun addTicket(ticket: Ticket)
    suspend fun updateTicket(ticketID: String, updatedTicket: Ticket)
}
