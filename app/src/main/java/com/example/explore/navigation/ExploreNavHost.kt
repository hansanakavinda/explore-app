package com.example.explore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.explore.feature.detail.CountryDetailRoute
import com.example.explore.feature.list.CountryListRoute

@Composable
fun ExploreNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ListRoute,
        modifier = modifier
    ) {
        composable<ListRoute> {
            CountryListRoute(
                onCountryClick = { code ->
                    navController.navigate(DetailRoute(countryCode = code))
                }
            )
        }
        composable<DetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<DetailRoute>()
            CountryDetailRoute(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
