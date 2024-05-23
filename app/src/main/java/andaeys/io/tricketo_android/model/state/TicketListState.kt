package andaeys.io.tricketo_android.model.state

import andaeys.io.tricketo_android.model.TicketItem

sealed class TicketListState {
    object Loading: TicketListState()
    object Empty: TicketListState()
    data class Success(
        val ticketItemList: List<TicketItem>
    ): TicketListState()
    data class Error(
        val errorMessage: String = "Unknown error"
    ): TicketListState()
}
