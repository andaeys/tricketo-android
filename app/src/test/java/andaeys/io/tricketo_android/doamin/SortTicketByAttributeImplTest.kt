package andaeys.io.tricketo_android.doamin

import andaeys.io.tricketo_android.model.TicketAttr
import andaeys.io.tricketo_android.model.TicketItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortTicketByAttributeImplTest {

    private lateinit var sortTicketByAttribute: SortTicketByAttribute
    private val ticketList = listOf(
        TicketItem("1", 1L, "123", "Zico", 2000, 3000),
        TicketItem("2", 2L, "456", "Badrun", 1500, 2500),
        TicketItem("3", 3L, "789", "Cuplis", 1800, 2800)
    )

    @Before
    fun setUp() {
        sortTicketByAttribute = SortTicketByAttributeImpl()
    }

    @Test
    fun `test sort by ENTER_TIME`() = runBlocking {
        val sortedList = sortTicketByAttribute.execute(ticketList, TicketAttr.ENTER_TIME)
        assertEquals(listOf(ticketList[2], ticketList[1], ticketList[0]), sortedList)
    }

    @Test
    fun `test sort by LICENSE_NUMBER`() = runBlocking {
        val sortedList = sortTicketByAttribute.execute(ticketList, TicketAttr.LICENSE_NUMBER)
        assertEquals(listOf(ticketList[0], ticketList[1], ticketList[2]), sortedList)
    }

    @Test
    fun `test sort by DRIVER_NAME`() = runBlocking {
        val sortedList = sortTicketByAttribute.execute(ticketList, TicketAttr.DRIVER_NAME)
        assertEquals(listOf(ticketList[1], ticketList[2], ticketList[0]), sortedList)
    }

    @Test
    fun `test sort by INBOUND_WEIGHT`() = runBlocking {
        val sortedList = sortTicketByAttribute.execute(ticketList, TicketAttr.INBOUND_WEIGHT)
        assertEquals(listOf(ticketList[0], ticketList[2], ticketList[1]), sortedList)
    }

    @Test
    fun `test sort by OUTBOUND_WEIGHT`() = runBlocking {
        val sortedList = sortTicketByAttribute.execute(ticketList, TicketAttr.OUTBOUND_WEIGHT)
        assertEquals(listOf(ticketList[0], ticketList[2], ticketList[1]), sortedList)
    }
}
