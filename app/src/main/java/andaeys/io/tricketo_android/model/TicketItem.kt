package andaeys.io.tricketo_android.model

import andaeys.io.tricketo_android.model.entity.Ticket

data class TicketItem(
    val ticketKey: String,
    var enterTime: Long = 0,
    var licenseNumber: String = "",
    var driverName: String = "",
    var inboundWeight: Int = 0,
    var outboundWeight: Int = 0
){
    companion object {
        fun fromTicketEntity(ticketKey: String?, ticket: Ticket?): TicketItem {
            return TicketItem(
                ticketKey = ticketKey?:"",
                enterTime = ticket?.enterTime?: 0,
                licenseNumber = ticket?.licenseNumber?:"",
                driverName = ticket?.driverName?:"",
                inboundWeight = ticket?.inboundWeight?:0,
                outboundWeight = ticket?.outboundWeight?:0
            )
        }

        fun toTicketEntity(ticket: TicketItem): Ticket {
            return Ticket(
                enterTime = ticket.enterTime,
                licenseNumber = ticket.licenseNumber,
                driverName = ticket.driverName,
                inboundWeight = ticket.inboundWeight,
                outboundWeight = ticket.outboundWeight
            )
        }
    }
}
