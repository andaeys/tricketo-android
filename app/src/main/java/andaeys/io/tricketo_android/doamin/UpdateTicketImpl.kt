package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.repository.TicketRepository

class UpdateTicketImpl(private val ticketRepository: TicketRepository) : UpdateTicket {
    override suspend fun execute(ticketItem: TicketItem) {
        ticketRepository.updateTicket(
            ticketItem.ticketKey,
            TicketItem.toTicketEntity(ticketItem)
        )
    }
}
