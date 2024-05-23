package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketAttr
import andaeys.io.tricketo_android.model.TicketItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortTicketByAttributeImpl : SortTicketByAttribute {
    override suspend fun execute(ticketList: List<TicketItem>, attr: TicketAttr):
            List<TicketItem> = withContext(Dispatchers.IO){
         when(attr){
            TicketAttr.ENTER_TIME -> {
                ticketList.sortedByDescending { it.enterTime }
            }
            TicketAttr.LICENSE_NUMBER -> {
                ticketList.sortedBy { it.licenseNumber }
            }
            TicketAttr.DRIVER_NAME -> {
                ticketList.sortedBy { it.driverName }
            }
            TicketAttr.INBOUND_WEIGHT -> {
                ticketList.sortedByDescending { it.inboundWeight }
            }
            TicketAttr.OUTBOUND_WEIGHT -> {
                ticketList.sortedByDescending { it.outboundWeight }
            }
        }
    }
}
