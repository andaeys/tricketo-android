package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketItem
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

class GetTicketListTest {

    @Mock
    private lateinit var ticketRepository: TicketRepository
    private lateinit var getTicketList: GetTicketList

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTicketList = GetTicketListImpl(ticketRepository)
    }

    @Test
    fun `execute should return list of ticket item when success`() = runBlocking {
        val expectedListSize = 3
        val ticketEntityList = dummyTicketList(expectedListSize)
        val expectedTicketItemList = ticketEntityList.mapIndexed { index, ticket ->
            TicketItem.fromTicketEntity("$index", ticket)
        }

        `when`(ticketRepository.getTickets())
            .thenReturn(expectedTicketItemList)

        val result = getTicketList.execute()

        assertEquals(expectedListSize, result.size)
        assert(expectedTicketItemList.any { item -> item.licenseNumber == result.first().licenseNumber })
    }

    @Test
    fun `execute should thrown error when repo is error`() = runBlocking {
        val expectedError = Exception("Something when wrong")
        `when`(ticketRepository.getTickets())
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java) {
            runBlocking { getTicketList.execute() }
        }

        assertEquals(expectedError.message, exception.message)
    }
}
