package andaeys.io.tricketo_android.viewmodels

import andaeys.io.tricketo_android.doamin.GetTicketList
import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.state.TicketListState
import andaeys.io.tricketo_android.repository.dummyTicketList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TicketLIstViewModelTest {
    @Mock
    private lateinit var getTicketList: GetTicketList
    private lateinit var viewModel: TicketLIstViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = TicketLIstViewModel(getTicketList)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `should return flow of state with loading and news data when fetch is success`()  = runTest {
        val expectedListSize = 3
        val ticketEntityList = dummyTicketList(expectedListSize)
        val expectedTicketItemList = ticketEntityList.mapIndexed { index, ticket ->
            TicketItem.fromTicketEntity("$index", ticket)
        }
        val expectedFlowCount = 2

        `when`(getTicketList.execute()).thenReturn(expectedTicketItemList)

        val states = mutableListOf<TicketListState>()
        val job = launch { viewModel.ticketListState.toList(states) }

        viewModel.fetchTicketList()
        advanceUntilIdle()

        assertEquals(expectedFlowCount, states.size)
        assertTrue(states[0] is TicketListState.Loading)
        job.cancel()
        assertTrue(states[1] is TicketListState.Success)
        if (states[1] is TicketListState.Success){
            val successState = states[1] as TicketListState.Success
            assertEquals(expectedTicketItemList.size, successState.ticketItemList.size)
            assertTrue(expectedTicketItemList.contains(successState.ticketItemList.first()))
        }
    }

    @Test
    fun `should return flow of state with loading and empty when fetch is success with empty result`() = runTest {
        val expectedFlowCount = 2

        `when`(getTicketList.execute()).thenReturn(emptyList())

        val states = mutableListOf<TicketListState>()
        val job = launch {
            viewModel.ticketListState.toList(states)
        }
        viewModel.fetchTicketList()
        advanceUntilIdle()

        assertEquals(expectedFlowCount, states.size)
        assertTrue(states[0] is TicketListState.Loading)
        job.cancel()
        assertTrue(states[1] is TicketListState.Empty)
    }

    @Test
    fun `should return flow of state with loading and error when fetch is failed`() = runTest {
        val expectedErrorMessage = "Firebase Error"
        val expectedFlowCount = 2

        //stubbing
        `when`(getTicketList.execute()).thenThrow(RuntimeException(expectedErrorMessage))

        //execution
        val states = mutableListOf<TicketListState>()
        val job = launch {
            viewModel.ticketListState.toList(states)
        }
        viewModel.fetchTicketList()
        advanceUntilIdle()

        //assertion
        assertEquals(expectedFlowCount, states.size)
        assertTrue(states[0] is TicketListState.Loading)
        job.cancel()
        assertTrue(states[1] is TicketListState.Error)
        if(states[1] is TicketListState.Error){
            val errorState = states[1] as TicketListState.Error
            assertEquals(expectedErrorMessage, errorState.errorMessage)
        }
    }

}
