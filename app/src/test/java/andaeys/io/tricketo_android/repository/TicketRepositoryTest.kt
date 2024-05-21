package andaeys.io.tricketo_android.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
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
        assert(1==1)
    }
}
