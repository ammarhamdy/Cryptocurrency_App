package com.am.cryptocurrency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.am.cryptocurrency.presentation.Screen
import com.am.cryptocurrency.presentation.coin_detail.CoinDetailViewModel
import com.am.cryptocurrency.presentation.coin_detail.CoinDetailsScreen
import com.am.cryptocurrency.presentation.coin_list.CoinListViewModel
import com.am.cryptocurrency.presentation.coin_list.CoinsListScreen
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme
import com.am.cryptocurrency.util.Constants.COIN_ID_PARAM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptocurrencyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ListScreen.route
                    ) {

                        composable(Screen.ListScreen.route){
                            val coinsListViewModel = hiltViewModel<CoinListViewModel>()
                            val navigate =  {
                                coinId: String -> navController.navigate(Screen.CoinDetailsScreen.route + "/${coinId}")
                            }
                            CoinsListScreen(
                                coinsStateFlow = coinsListViewModel.coinsState,
                                onClick = navigate,
                                reload = coinsListViewModel::reload,
                                Modifier
                                    .fillMaxSize()
                                    .padding(
                                        horizontal = dimensionResource(R.dimen.small_padding),
                                        vertical = innerPadding.calculateTopPadding()
                                    )
                            )
                        }

                        composable(route = Screen.CoinDetailsScreen.route + "/{$COIN_ID_PARAM}"){
                            val detailViewModel = hiltViewModel<CoinDetailViewModel>()
                            CoinDetailsScreen(
                                detailViewModel,
                                Modifier
                                    .fillMaxSize()
                                    .padding(
                                        horizontal = dimensionResource(R.dimen.small_padding),
                                        vertical = innerPadding.calculateTopPadding()
                                    )
                            )
                        }

                    }
                }
            }
        }
    }
}

