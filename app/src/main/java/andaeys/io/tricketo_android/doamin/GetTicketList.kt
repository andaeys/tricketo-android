package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem

interface GetTicketList {
    suspend fun execute(): List<TicketItem>
}
