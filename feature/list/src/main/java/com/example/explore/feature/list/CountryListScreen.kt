package com.example.explore.feature.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.explore.CountryListItem
import com.example.explore.ListHeader
import com.example.explore.SearchBar
import com.example.explore.core.ui.R
import com.example.explore.ui.theme.ExploreTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.compose.runtime.getValue

data class CountryItemUi(
    val code: String,
    val name: String,
    val capital: String,
    val region: String,
    val flagUrl: String = ""
)

sealed interface TempListState {
    data object Loading : TempListState
    data object Empty : TempListState
    data class Error(val message: String) : TempListState
    data class Success(val countries: List<CountryItemUi>) : TempListState
}

val fakeCountries = listOf(
    CountryItemUi("LKA", "Sri Lanka", "Colombo", "Asia"),
    CountryItemUi("JPN", "Japan", "Tokyo", "Asia"),
    CountryItemUi("FRA", "France", "Paris", "Europe"),
    CountryItemUi("AUS", "Australia", "Canberra", "Oceania"),
    CountryItemUi("CAN", "Canada", "Ottawa", "North America"),
    CountryItemUi("KOR", "South Korea", "Seoul", "Asia"),
    CountryItemUi("BRA", "Brazil", "Brasília", "South America"),
    CountryItemUi("EGY", "Egypt", "Cairo", "Africa"),
    CountryItemUi("ITA", "Italy", "Rome", "Europe"),
    CountryItemUi("IND", "India", "New Delhi", "Asia")
)



@Composable
fun CountryListRoute(
    onCountryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    CountryListScreen(
        state = state,
        query = query,
        onQueryChange = viewModel::onQueryChange,
        onCountryClick = onCountryClick,
        onRetry = { /* Retry logic here */ },
        modifier = modifier
    )
}

@Composable
fun CountryListScreen(
    state: TempListState,
    query: String,
    onQueryChange: (String) -> Unit,
    onCountryClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        ListHeader()
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(Modifier.height(16.dp))
            SearchBar(query = query, onQueryChange = onQueryChange)
            Spacer(Modifier.height(16.dp))

            when (state) {
                is TempListState.Loading -> LoadingList()
                is TempListState.Empty -> EmptyView()
                is TempListState.Error -> ErrorView(state.message, onRetry)
                is TempListState.Success -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.countries, key = { it.code }) { country ->
                        CountryListItem(
                            name = country.name,
                            capital = country.capital,
                            region = country.region,
                            flagUrl = country.flagUrl,
                            onClick = { onCountryClick(country.code) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingList() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(6) { LoadingItem() }
    }
}

@Composable
fun LoadingItem() {
    val grey = Color(0xFFF0F4FA)
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color(0xFFE3E6EC))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(72.dp, 48.dp).background(grey, RoundedCornerShape(4.dp)))
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(80.dp, 12.dp).background(grey, RoundedCornerShape(6.dp)))
                Box(Modifier.size(140.dp, 12.dp).background(grey, RoundedCornerShape(6.dp)))
                Box(Modifier.size(50.dp, 12.dp).background(grey, RoundedCornerShape(6.dp)))
            }
        }
    }
}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_globe_empty),
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "No countries found",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Try searching with a different keyword.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Something went wrong",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}

@Preview(name = "Success", showBackground = true, heightDp = 800)
@Composable
fun ListSuccessPreview() {
    ExploreTheme {
        CountryListScreen(TempListState.Success(fakeCountries), "", {}, {}, {})
    }
}

@Preview(name = "Loading", showBackground = true, heightDp = 800)
@Composable
fun ListLoadingPreview() {
    ExploreTheme {
        CountryListScreen(TempListState.Loading, "", {}, {}, {})
    }
}

@Preview(name = "Empty", showBackground = true, heightDp = 800)
@Composable
fun ListEmptyPreview() {
    ExploreTheme {
        CountryListScreen(TempListState.Empty, "xyz", {}, {}, {})
    }
}

@Preview(name = "Error", showBackground = true, heightDp = 800)
@Composable
fun ListErrorPreview() {
    ExploreTheme {
        CountryListScreen(TempListState.Error("Check your connection."), "", {}, {}, {})
    }
}
