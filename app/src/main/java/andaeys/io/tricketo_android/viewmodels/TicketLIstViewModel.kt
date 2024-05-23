package andaeys.io.tricketo_android.viewmodels

import andaeys.io.tricketo_android.doamin.GetTicketList
import andaeys.io.tricketo_android.doamin.SortTicketByAttribute
import andaeys.io.tricketo_android.model.TicketAttr
import andaeys.io.tricketo_android.model.state.TicketListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketLIstViewModel(
    private val getTicketList: GetTicketList,
    private val sortTicketByAttribute: SortTicketByAttribute
): ViewModel() {
    private val _ticketListState = MutableStateFlow<TicketListState>(TicketListState.Loading)
    val ticketListState: StateFlow<TicketListState> = _ticketListState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _ticketListState.value = TicketListState.Error(exception.message ?: "Unknown error")
    }

    fun fetchTicketList() {
        _ticketListState.value = TicketListState.Loading
        viewModelScope.launch(exceptionHandler) {
            val ticketItemList = getTicketList.execute()
            val state: TicketListState = if (ticketItemList.isNotEmpty()) {
                TicketListState.Success(ticketItemList = ticketItemList)
            } else {
                TicketListState.Empty
            }
            _ticketListState.value = state
        }
    }

    fun sortTicketBy(ticketAttr: TicketAttr) {
        viewModelScope.launch(exceptionHandler) {
            val currentState = _ticketListState.value
            if (currentState is TicketListState.Success) {
                val sortedList = sortTicketByAttribute.execute(currentState.ticketItemList, ticketAttr)
                _ticketListState.value = TicketListState.Success(ticketItemList = sortedList)
            }
        }

    }
}
