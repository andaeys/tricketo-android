package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.repository.TicketRepository
import andaeys.io.tricketo_android.repository.dummyTicketList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

class UpdateTicketTest {

    @Mock
    private lateinit var ticketRepository: TicketRepository
    private lateinit var updateTicket: UpdateTicket

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updateTicket = UpdateTicketImpl(ticketRepository)
    }

    @Test
    fun `execute should complete when repository has no error`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val ticketKey = "ticket_key"
        val ticketItem = TicketItem.fromTicketEntity(ticketKey, ticket)

        updateTicket.execute(ticketItem)
        val strCaptor = argumentCaptor<String>()
        val ticketCaptor = argumentCaptor<Ticket>()

        verify(ticketRepository).updateTicket(strCaptor.capture(), ticketCaptor.capture())
    }

    @Test
    fun `execute should thrown error when repo is error`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val ticketKey = "ticket_key"
        val ticketItem = TicketItem.fromTicketEntity(ticketKey, ticket)

        val expectedError = Exception("Something when wrong")
        `when`(ticketRepository.updateTicket(ticketKey, ticket))
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java) {
            runBlocking { updateTicket.execute(ticketItem) }
        }

        assertEquals(expectedError.message, exception.message)
    }
}
