package com.example.explore.feature.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.explore.core.model.Country
import com.example.explore.feature.list.mapper.asUiModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog

class CountryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchBar_updatesQuery() {
        val fakeCountries = flowOf(PagingData.from(listOf(
            Country("LKA", "Sri Lanka", "Colombo", "Asia", "South Asia", "22M", "65k", "", emptyList(), emptyList(), emptyList()).asUiModel()
        )))
        
        var queryValue = ""

        composeTestRule.setContent {
            val pagedItems = fakeCountries.collectAsLazyPagingItems()
            CountryListScreen(
                countries = pagedItems,
                query = queryValue,
                onQueryChange = { queryValue = it },
                onCountryClick = {},
                onRetry = {}
            )
        }

        // Assuming SearchBar has a text field that can be found by content description or tag
        // Since we don't have the exact SearchBar implementation, we can look for placeholder or typical text
        composeTestRule.onNodeWithContentDescription("Search", useUnmergedTree = true)
            .performTextInput("Sri Lanka")
            
        assert(queryValue == "Sri Lanka")
    }
}
