package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.repository.TicketRepository

class AddTicketImpl(private val ticketRepository: TicketRepository) : AddTicket {
    override suspend fun execute(ticket: Ticket) {
        ticketRepository.addTicket(ticket)
    }
}
