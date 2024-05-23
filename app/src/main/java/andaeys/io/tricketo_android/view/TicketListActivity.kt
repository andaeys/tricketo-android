package andaeys.io.tricketo_android.view

import andaeys.io.tricketo_android.R
import andaeys.io.tricketo_android.model.TicketAttr
import andaeys.io.tricketo_android.model.TicketItem
import andaeys.io.tricketo_android.model.state.TicketListState
import andaeys.io.tricketo_android.viewmodels.TicketLIstViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketListActivity : ComponentActivity() {

    private val viewModel: TicketLIstViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.ticketListState.collectAsState()
            TicketListScreen(
                state = state,
                onRefresh = {viewModel.fetchTicketList()},
                onSortBy = {attr -> viewModel.sortTicketBy(attr)},
                onAddClick = {},
                onItemClick = {ticeketItem ->}
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTicketList()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketListScreen(
    state: TicketListState,
    onRefresh: () -> Unit = {},
    onSortBy: (TicketAttr) -> Unit,
    onAddClick: () -> Unit = {},
    onItemClick: (TicketItem) -> Unit = {}
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onAddClick()},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Ticket")
            }
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state is TicketListState.Loading),
            onRefresh = { onRefresh() }
        ) {
            when (state) {
                is TicketListState.Loading -> LoadingView()
                is TicketListState.Error -> {
                    ErrorView(state.errorMessage) { onRefresh() }
                }
                is TicketListState.Empty -> EmptyView()
                is TicketListState.Success -> TicketList(
                    tickets = state.ticketItemList,
                    onSort = { attr -> onSortBy(attr) },
                    onTicketClick = { ticket -> onItemClick(ticket) }
                )
            }
        }
    }
}

@Composable
fun TicketList(
    tickets: List<TicketItem>,
    onSort: (TicketAttr) -> Unit,
    onTicketClick: (TicketItem) -> Unit
) {
    Column {
        SortOptions(onSort)
        LazyColumn {
            items(tickets) { ticket ->
                TicketCard(ticket, onTicketClick)
            }
        }
    }
}

@Composable
fun SortOptions(onSort: (TicketAttr) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Sort by:")
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onSort(TicketAttr.ENTER_TIME) }) { Text(TicketAttr.ENTER_TIME.description) }
            Button(onClick = { onSort(TicketAttr.DRIVER_NAME) }) { Text(TicketAttr.DRIVER_NAME.description) }
            Button(onClick = { onSort(TicketAttr.LICENSE_NUMBER) }) { Text(TicketAttr.LICENSE_NUMBER.description) }
        }
    }
}

@Composable
fun TicketCard(ticket: TicketItem, onTicketClick: (TicketItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTicketClick(ticket) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("License Number: ${ticket.licenseNumber}")
            Text("Driver: ${ticket.driverName}")
            Text("Enter Time: ${ticket.enterTime}")
            Text("Inbound Weight: ${ticket.inboundWeight}")
            Text("Outbound Weight: ${ticket.outboundWeight}")
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(onClick = { /* TODO: View/edit ticket */ }) { Text("View/Edit") }
//            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val aComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        LottieAnimation(
            composition = aComposition ,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(1200.dp)
        )
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val aComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search_not_found))
        LottieAnimation(
            composition = aComposition ,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(480.dp)
        )
    }
}

@Composable
fun ErrorView(errorMessage: String, onRefreshClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Log.d("Search error", errorMessage)
            val aComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
            LottieAnimation(
                composition = aComposition ,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(640.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onRefreshClick() }) {
                Text("Retry")
            }
        }
    }
}



