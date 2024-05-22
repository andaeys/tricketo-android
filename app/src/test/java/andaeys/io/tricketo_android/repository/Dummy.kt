package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.Ticket
import java.util.Date

fun dummyTicketList(size: Int = 1): List<Ticket> {
    return (1..size).map {
        var driverName = ""
        repeat(4){driverName+=('A'..'Z').random()}
        driverName+= " "
        repeat(6){driverName+=('A'..'Z').random()}

        var licenceNumber = ""
        repeat(5){licenceNumber+="${(0..9).random()}"}
        val timeEnter = Date().time
        return@map Ticket(
            enterTime = timeEnter,
            licenseNumber = licenceNumber,
            driverName = driverName,
            inboundWeight = (50..5000).random(),
            outboundWeight = (6000..10000).random()
        )
    }.toList()
}
