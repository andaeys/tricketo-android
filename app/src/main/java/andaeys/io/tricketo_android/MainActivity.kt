package andaeys.io.tricketo_android

import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.repository.TicketRepository
import andaeys.io.tricketo_android.ui.theme.TricketoandroidTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Date

class MainActivity : ComponentActivity() {

    private val repository: TicketRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TricketoandroidTheme {
                var tickets by remember { mutableStateOf(emptyList<Ticket>()) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        try {
                            tickets = repository.getTickets()
                        } catch (e: Exception) {
                            errorMessage = e.message
                        }
                    }
                }
                Column {
                    errorMessage?.let {
                        Text("Error: $it", color = MaterialTheme.colorScheme.error)
                    }
                    // Display tickets
                    tickets.forEach { ticket ->
                        Text("Driver: ${ticket.driverName}, License: ${ticket.licenseNumber}")
                    }

                    Button(onClick = {
                        var driverName = ""
                        repeat((1..7).count()) { driverName += ('A'..'Z').random() }
                        var licenceNumber = ""
                        repeat((1..5).count()) {licenceNumber += "${(0..9).random()}" }
                        coroutineScope.launch {
                            val timeEnter = Date().time
                            val newTicket = Ticket(
                                enterTime = timeEnter,
                                licenseNumber = licenceNumber,
                                driverName = driverName,
                                inboundWeight = (50..5000).random(),
                                outboundWeight = (6000..10000).random()
                            )
                            repository.addTicket(newTicket)
                            tickets = repository.getTickets()
                        }

                    }) {
                        Text("Add Ticket")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TricketoandroidTheme {
        Greeting("Android")
    }
}