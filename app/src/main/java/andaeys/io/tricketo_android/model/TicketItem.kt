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

    fun fromTicketEntity(ticketKey: String, ticket: Ticket): TicketItem {
        return TicketItem(
            ticketKey = ticketKey,
            enterTime = ticket.enterTime,
            licenseNumber = ticket.licenseNumber,
            driverName = ticket.driverName,
            inboundWeight = ticket.inboundWeight,
            outboundWeight = ticket.outboundWeight
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
