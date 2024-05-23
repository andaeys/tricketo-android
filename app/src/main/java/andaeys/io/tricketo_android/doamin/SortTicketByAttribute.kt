package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketAttr
import andaeys.io.tricketo_android.model.TicketItem

interface SortTicketByAttribute {
    suspend fun execute(ticketList: List<TicketItem>, attr: TicketAttr):List<TicketItem>
}