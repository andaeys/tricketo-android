package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.repository.TicketRepository

class GetTicketListImpl(private val ticketRepository: TicketRepository) : GetTicketList {
    override suspend fun execute(): List<TicketItem> = ticketRepository.getTickets()
}
