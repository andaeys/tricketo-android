package andaeys.io.tricketo_android.view

import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.state.TicketEditState
import andaeys.io.tricketo_android.viewmodels.TicketEditViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketEditActivity : ComponentActivity() {

    private val viewModel: TicketEditViewModel by viewModel()

    companion object {
        const val EXTRA_TICKET_ITEM = "EXTRA_TICKET_ITEM"
        fun start(context: Context, ticket: TicketItem? = null) {
            val intent = Intent(context, TicketEditActivity::class.java).apply {
                putExtra(EXTRA_TICKET_ITEM, TicketItem.toJSON(ticket))
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ticketJSON = intent.getStringExtra(EXTRA_TICKET_ITEM)
            val ticketItem = if(!ticketJSON.isNullOrEmpty()){
                TicketItem.fromJSON(ticketJSON)
            } else{
                null
            }

            setContent {
                val state by viewModel.editState.collectAsState()

                when (state) {
                    is TicketEditState.Success -> {
                        // Navigate back to the ticket list
                        finish()
                    }
                    is TicketEditState.Error -> {
                        val errorMessage = (state as TicketEditState.Error).errorMessage
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }

                TicketEditScreen(
                    ticketItem = ticketItem,
                    onSave = { newTicket, key ->
                        if (ticketItem == null) {
                            viewModel.addNewTicket(newTicket)
                        } else {
                            viewModel.updateExistingTicket(
                                TicketItem.fromTicketEntity(key, newTicket)
                            )
                        }
                    },
                    onCancel = {
                        finish()
                    }
                )
            }

        }
    }
}


//@Preview(showBackground = true)
//@Composable
