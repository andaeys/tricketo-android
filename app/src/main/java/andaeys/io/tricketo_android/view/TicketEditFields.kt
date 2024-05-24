package andaeys.io.tricketo_android.view


import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.utils.dateStringToLong
import andaeys.io.tricketo_android.utils.toFormattedFullString
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun TicketEditScreen(
    ticketItem: TicketItem?,
    onSave: (Ticket, String?) -> Unit,
    onCancel: () -> Unit
) {
    var dateLong by remember { mutableStateOf(ticketItem?.enterTime ?: System.currentTimeMillis()) }
    var dateString by remember { mutableStateOf(ticketItem?.enterTime?.toFormattedFullString()?: System.currentTimeMillis().toFormattedFullString()) }
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
        OutlinedTextField(
            value = dateString,
            onValueChange = {
                dateLong = dateStringToLong(it)?: System.currentTimeMillis()
                dateString = it
            },
            label = { Text("Enter time (format MMM dd, yyyy - HH:mm)") },
            modifier = Modifier.fillMaxWidth()
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
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(onClick = {
                val newTicket = Ticket(
                    enterTime = dateLong,
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
