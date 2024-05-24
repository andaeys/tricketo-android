package andaeys.io.tricketo_android.viewmodels

import andaeys.io.tricketo_android.doamin.AddTicket
import andaeys.io.tricketo_android.doamin.UpdateTicket
import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.model.state.TicketEditState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class TicketEditViewModelTest {

    @Mock
    private lateinit var addTicket: AddTicket

    @Mock
    private lateinit var updateTicket: UpdateTicket

    private lateinit var viewModel: TicketEditViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = TicketEditViewModel(addTicket, updateTicket)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addNewTicket should update state to Success when ticket is valid`() = runTest {
        val validTicket = Ticket(
            enterTime = System.currentTimeMillis(),
            licenseNumber = "12345",
            driverName = "Fulan Chow",
            inboundWeight = 1000,
            outboundWeight = 500
        )

        viewModel.addNewTicket(validTicket)

        advanceUntilIdle()

        val state = viewModel.editState.value
        assertTrue(state is TicketEditState.Success)
        verify(addTicket).execute(validTicket)
    }

    @Test
    fun `addNewTicket should update state to Error when ticket is invalid`() = runTest {
        val invalidTicket = Ticket(
            enterTime = 0,
            licenseNumber = "",
            driverName = "",
            inboundWeight = 0,
            outboundWeight = 0
        )

        viewModel.addNewTicket(invalidTicket)

        advanceUntilIdle()

        val state = viewModel.editState.value
        assertTrue(state is TicketEditState.Error)
        verify(addTicket, never()).execute(any())
    }

    @Test
    fun `updateExistingTicket should update state to Success when ticket is valid`() = runTest {
        val validTicketItem = TicketItem(
            ticketKey = "123",
            enterTime = System.currentTimeMillis(),
            licenseNumber = "12345",
            driverName = "Fulan Chow",
            inboundWeight = 1000,
            outboundWeight = 500
        )

        viewModel.updateExistingTicket(validTicketItem)

        advanceUntilIdle()

        val state = viewModel.editState.value
        assertTrue(state is TicketEditState.Success)
        verify(updateTicket).execute(validTicketItem)
    }

    @Test
    fun `updateExistingTicket should update state to Error when ticket is invalid`() = runTest {
        val invalidTicketItem = TicketItem(
            ticketKey = "123",
            enterTime = 0,
            licenseNumber = "",
            driverName = "",
            inboundWeight = 0,
            outboundWeight = 0
        )

        viewModel.updateExistingTicket(invalidTicketItem)

        advanceUntilIdle()

        val state = viewModel.editState.value
        assertTrue(state is TicketEditState.Error)
        verify(updateTicket, never()).execute(any())
    }
}
