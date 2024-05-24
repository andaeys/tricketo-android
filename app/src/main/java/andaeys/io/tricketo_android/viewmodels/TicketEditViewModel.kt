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
            addTicket.execute(ticket)
            _editState.value = TicketEditState.Success
        }
    }

    fun updateExistingTicket(ticket: TicketItem) {
        viewModelScope.launch(exceptionHandler) {
            updateTicket.execute(ticket)
            _editState.value = TicketEditState.Success
        }
    }
}
