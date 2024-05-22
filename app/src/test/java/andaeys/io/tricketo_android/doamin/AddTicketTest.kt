package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.entity.Ticket
import andaeys.io.tricketo_android.repository.TicketRepository
import andaeys.io.tricketo_android.repository.dummyTicketList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor

class AddTicketTest {

    @Mock
    private lateinit var ticketRepository: TicketRepository
    private lateinit var addTicket: AddTicket

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addTicket = AddTicketImpl(ticketRepository)
    }

    @Test
    fun `execute should complete when repository has no error`() = runBlocking {
        val ticket = dummyTicketList(1).first()

        addTicket.execute(ticket)
        val ticketCaptor = argumentCaptor<Ticket>()

        verify(ticketRepository).addTicket(ticketCaptor.capture())
    }

    @Test
    fun `execute should thrown error when repo is error`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val expectedError = Exception("Something when wrong")
        `when`(ticketRepository.addTicket(ticket))
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java) {
            runBlocking { addTicket.execute(ticket) }
        }

        assertEquals(expectedError.message, exception.message)
    }
}