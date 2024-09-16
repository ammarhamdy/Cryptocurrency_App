package com.am.cryptocurrency.presentation

sealed class Screen(val route: String) {
    data object ListScreen: Screen("list_screen")
    data object CoinDetailsScreen: Screen("details_screen")
}