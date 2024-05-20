package andaeys.io.tricketo_android

import andaeys.io.tricketo_android.db.DatabaseManager
import andaeys.io.tricketo_android.model.Ticket
import andaeys.io.tricketo_android.ui.theme.TricketoandroidTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Date

class MainActivity : ComponentActivity() {

    private val databaseManager = DatabaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TricketoandroidTheme {
                var tickets by remember { mutableStateOf(emptyList<Ticket>()) }
                LaunchedEffect(Unit) {
                    databaseManager.getTickets {
                        tickets = it
                    }
                }
                Column {
                    // Display tickets
                    tickets.forEach { ticket ->
                        Text("Driver: ${ticket.driverName}, License: ${ticket.licenseNumber}")
                    }

                    Button(onClick = {
                        var driverName = ""
                        repeat((1..7).count()) { driverName += ('A'..'Z').random() }
                        var licenceNumber = ""
                        repeat((1..5).count()) {licenceNumber += "${(0..9).random()}" }
                        val newTicket = Ticket(
                            enterTime = Date().time,
                            licenseNumber = licenceNumber,
                            driverName = driverName,
                            inboundWeight = (50..5000).random(),
                            outboundWeight = (6000..10000).random()
                        )
                        databaseManager.addTicket(newTicket)
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