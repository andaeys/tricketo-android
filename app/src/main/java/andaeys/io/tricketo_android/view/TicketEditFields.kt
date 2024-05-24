package andaeys.io.tricketo_android.view


import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TicketEditScreen(
    ticketItem: TicketItem?,
    onSave: (Ticket, String?) -> Unit,
    onCancel: () -> Unit
) {
    var date by remember { mutableStateOf(ticketItem?.enterTime ?: System.currentTimeMillis()) }
    var licenseNumber by remember { mutableStateOf(ticketItem?.licenseNumber ?: "") }
    var driverName by remember { mutableStateOf(ticketItem?.driverName ?: "") }
    var inboundWeight by remember { mutableStateOf(ticketItem?.inboundWeight?.toString() ?: "") }
    var outboundWeight by remember { mutableStateOf(ticketItem?.outboundWeight?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        DatePicker(
            label = "Enter Time",
            date = date,
            onDateChanged = { date = it }
        )
        OutlinedTextField(
            value = licenseNumber,
            onValueChange = { licenseNumber = it },
            label = { Text("License Number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = driverName,
            onValueChange = { driverName = it },
            label = { Text("Driver Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = inboundWeight,
            onValueChange = { inboundWeight = it },
            label = { Text("Inbound Weight") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = outboundWeight,
            onValueChange = { outboundWeight = it },
            label = { Text("Outbound Weight") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = {
                val newTicket = Ticket(
                    enterTime = date,
                    licenseNumber = licenseNumber,
                    driverName = driverName,
                    inboundWeight = inboundWeight.toIntOrNull() ?: 0,
                    outboundWeight = outboundWeight.toIntOrNull() ?: 0
                )
                onSave(newTicket, ticketItem?.ticketKey)
            }) {
                Text("Save")
            }
        }
    }
}

@Composable
fun DatePicker(
    label: String,
    date: Long,
    onDateChanged: (Long) -> Unit
) {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = formatter.format(Date(date))

    OutlinedTextField(
        value = formattedDate,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        enabled = false
    )
    Button(onClick = {
        onDateChanged(date)
    }) {
        Text("Pick Date")
    }
}