package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.entity.Ticket

interface AddTicket {
    suspend fun execute(ticket: Ticket)
}
