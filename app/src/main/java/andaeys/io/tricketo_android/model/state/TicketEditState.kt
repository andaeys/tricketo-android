package andaeys.io.tricketo_android.model.state

sealed class TicketEditState {
    object Editing: TicketEditState()
    object Success: TicketEditState()
    data class Error(val errorMessage: String): TicketEditState()
}
