package andaeys.io.tricketo_android.repository

import andaeys.io.tricketo_android.model.entity.Ticket
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TicketRepositoryTest {
    @Mock
    private lateinit var firebaseDatabase: FirebaseDatabase
    @Mock
    private lateinit var ticketsRef: DatabaseReference
    private lateinit var repository: TicketRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(firebaseDatabase.getReference(any()))
            .thenReturn(ticketsRef)
        repository = TicketRepositoryImpl(firebaseDatabase)
    }

    @Test
    fun `getTicket should return list of ticket when success`() = runBlocking {
        val expectedTicketListSize = 5
        val expectedTicketList = dummyTicketList(expectedTicketListSize)

        val dataSnapshot = mock(DataSnapshot::class.java)
        val childDataSnapshot =  expectedTicketList.mapIndexed {i, ticket ->
            mock(DataSnapshot::class.java).apply {
                `when`(getValue(Ticket::class.java)).thenReturn(ticket)
                `when`(key).thenReturn("$i")
            }
        }
        `when`(dataSnapshot.children).thenReturn(childDataSnapshot.asIterable())
        `when`(dataSnapshot.childrenCount).thenReturn(expectedTicketListSize.toLong())
        val taskCompletionSource = TaskCompletionSource<DataSnapshot>()
        taskCompletionSource.setResult(dataSnapshot)
        val mockTask: Task<DataSnapshot> = taskCompletionSource.task
        `when`(ticketsRef.get()).thenReturn(mockTask)

        val result = repository.getTickets()

        assertEquals(expectedTicketListSize, result.size)
    }

    @Test
    fun `getTicket should return empty list of ticket when success but empty`() = runBlocking {
        val expectedTicketListSize = 0

        val dataSnapshot = mock(DataSnapshot::class.java)
        `when`(dataSnapshot.childrenCount).thenReturn(0)
        val taskCompletionSource = TaskCompletionSource<DataSnapshot>()
        taskCompletionSource.setResult(dataSnapshot)
        val mockTask: Task<DataSnapshot> = taskCompletionSource.task
        `when`(ticketsRef.get()).thenReturn(mockTask)

        val result = repository.getTickets()

        assertEquals(expectedTicketListSize,result.size)
    }

    @Test
    fun `getTicket should throw error when error occurred`() = runBlocking {
        val expectedError = Exception("Something when wrong")

        `when`(ticketsRef.get())
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java){
            runBlocking { repository.getTickets() }
        }

        assertEquals(expectedError.message, exception.message)
    }

    @Test
    fun `addTicket should complete with no error when success`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val mockNewTicketRef = mock(DatabaseReference::class.java)
        val taskCompletionSource = TaskCompletionSource<Void>()
        taskCompletionSource.setResult(null)
        val mockTask: Task<Void> = taskCompletionSource.task

        val dummyTicketKey = "unique_ticket"
        `when`(ticketsRef.push()).thenReturn(mockNewTicketRef)
        `when`(mockNewTicketRef.key).thenReturn(dummyTicketKey)
        `when`(ticketsRef.child(dummyTicketKey)).thenReturn(mockNewTicketRef)
        `when`(mockNewTicketRef.setValue(ticket)).thenReturn(mockTask)

        repository.addTicket(ticket)

        verify(mockNewTicketRef).setValue(ticket)
        assertNotNull(mockNewTicketRef.key)
    }

    @Test
    fun `addTicket should throw error when error occurred`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val expectedError = Exception("Something when wrong")

        `when`(ticketsRef.push())
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java){
            runBlocking { repository.addTicket(ticket) }
        }

        assertEquals(expectedError.message, exception.message)
    }

    @Test
    fun `updateTicket should complete when success`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val ticketKey = "ticket_key"
        val mockNewTicketRef = mock(DatabaseReference::class.java)
        val taskCompletionSource = TaskCompletionSource<Void>()
        taskCompletionSource.setResult(null)
        val mockTask: Task<Void> = taskCompletionSource.task

        `when`(mockNewTicketRef.key).thenReturn(ticketKey)
        `when`(ticketsRef.child(ticketKey)).thenReturn(mockNewTicketRef)
        `when`(mockNewTicketRef.setValue(ticket)).thenReturn(mockTask)

        repository.updateTicket(ticketKey, ticket)

        verify(mockNewTicketRef).setValue(ticket)
        assertNotNull(ticket)
    }

    @Test
    fun `updateTicket should throw error when error occurred`() = runBlocking {
        val ticket = dummyTicketList(1).first()
        val ticketKey = "ticket_key"
        val expectedError = Exception("Something when wrong")

        `when`(ticketsRef.child(ticketKey))
            .thenAnswer { throw expectedError }

        val exception = assertThrows(Exception::class.java){
            runBlocking { repository.updateTicket(ticketKey, ticket) }
        }

        assertEquals(expectedError.message, exception.message)
    }
}
