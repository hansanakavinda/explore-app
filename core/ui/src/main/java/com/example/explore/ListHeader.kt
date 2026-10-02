package com.example.explore

import com.example.explore.core.ui.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.explore.ui.components.ExploreHeader
import com.example.explore.ui.components.ExploreSearchField
import com.example.explore.ui.theme.BrandBlue


@Composable
fun ListHeader(modifier: Modifier = Modifier) {
    ExploreHeader(
        title = "Explore",
        subtitle = "Discover countries, cultures and new destinations",
        modifier = modifier,
        illustration = {
            Image(
                painter = painterResource(R.drawable.ic_globe_header),
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
        }
    )
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ExploreSearchField(query, onQueryChange, modifier)
}

@Composable
fun DetailHeader(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = BrandBlue,
        contentColor = Color.White,
        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .semantics { heading() }
            )
            Image(
                painter = painterResource(R.drawable.ic_globe_header),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}