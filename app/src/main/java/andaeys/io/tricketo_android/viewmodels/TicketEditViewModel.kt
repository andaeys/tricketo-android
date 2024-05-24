package andaeys.io.tricketo_android.viewmodels

import andaeys.io.tricketo_android.doamin.AddTicket
import andaeys.io.tricketo_android.doamin.UpdateTicket
import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.model.state.TicketEditState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketEditViewModel(
    private val addTicket: AddTicket,
    private val updateTicket: UpdateTicket
) : ViewModel() {

    private val _editState = MutableStateFlow<TicketEditState>(TicketEditState.Editing)
    val editState: StateFlow<TicketEditState> = _editState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _editState.value = TicketEditState.Error(exception.message ?: "Unknown error")
    }

    fun addNewTicket(ticket: Ticket) {
        viewModelScope.launch(exceptionHandler) {
            val isValid = checkValid(ticket)
            if (isValid) {
                addTicket.execute(ticket)
                _editState.value = TicketEditState.Success
            } else{
                throw Exception("Input not valid, please check again")
            }

        }
    }

    fun updateExistingTicket(ticket: TicketItem) {
        viewModelScope.launch(exceptionHandler) {
            val isValid = checkValid(TicketItem.toTicketEntity(ticket))
            if (isValid) {
                updateTicket.execute(ticket)
                _editState.value = TicketEditState.Success
            } else {
                throw Exception("Input not valid, please check again")
            }
        }
    }

    private fun checkValid(ticket: Ticket): Boolean =
        ticket.driverName.isNotBlank() &&
                ticket.licenseNumber.isNotBlank() &&
                ticket.inboundWeight>0 &&
                ticket.outboundWeight>0 &&
                ticket.enterTime>0
}
