package com.example.explore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import com.example.explore.ui.theme.ExploreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExploreTheme(darkTheme = false, dynamicColor = false) {
                Scaffold { innerPadding ->
                    var query by remember { mutableStateOf("") }
                    val filtered = fakeCountries.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    CountryListScreen(
                        state = if (filtered.isEmpty()) TempListState.Empty
                        else TempListState.Success(filtered),
                        query = query,
                        onQueryChange = { query = it },
                        onCountryClick = { /* navigation comes from Hansana */ },
                        onRetry = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}