package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem

interface UpdateTicket {
    suspend fun execute(ticketItem: TicketItem)
}
