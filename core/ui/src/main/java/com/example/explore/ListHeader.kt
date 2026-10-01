package com.example.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.explore.core.ui.R
import com.example.explore.ui.theme.ExploreTheme

@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Explore", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
            Text(
                "Discover countries, cultures and new destinations",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_globe_header),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Search countries") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F4FA),
            unfocusedContainerColor = Color(0xFFF0F4FA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ListHeaderPreview() {
    ExploreTheme {
        Column(Modifier.padding(16.dp)) {
            ListHeader()
            Spacer(Modifier.height(16.dp))
            SearchBar(query = "", onQueryChange = {})
        }
    }
}
