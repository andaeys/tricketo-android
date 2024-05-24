package andaeys.io.tricketo_android.view

import andaeys.io.tricketo_android.model.TicketItem
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TicketDetailActivity : ComponentActivity() {

    companion object {
        private const val EXTRA_TICKET_ITEM = "EXTRA_TICKET_ITEM"
        fun start(context: Context, ticket: TicketItem? = null) {
            val intent = Intent(context, TicketDetailActivity::class.java).apply {
                putExtra(EXTRA_TICKET_ITEM, TicketItem.toJSON(ticket))
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ticketJSON = intent.getStringExtra(EXTRA_TICKET_ITEM)
        val ticketItem = if(!ticketJSON.isNullOrEmpty()){
            TicketItem.fromJSON(ticketJSON)
        } else{
            null
        }

        setContent {
            ticketItem?.let { ticket ->
                TicketDetailScreen(ticketItem = ticket)
            }
        }
    }
}

@Composable
fun TicketDetailScreen(ticketItem: TicketItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ticket Detail",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TicketDetailItem(label = "Enter Time:", value = ticketItem.enterTime.toString())
        TicketDetailItem(label = "License Number:", value = ticketItem.licenseNumber)
        TicketDetailItem(label = "Driver Name:", value = ticketItem.driverName)
        TicketDetailItem(label = "Inbound Weight:", value = "${ticketItem.inboundWeight} tons")
        TicketDetailItem(label = "Outbound Weight:", value = "${ticketItem.outboundWeight} tons")
    }
}

@Composable
fun TicketDetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp),
        )
    }
}