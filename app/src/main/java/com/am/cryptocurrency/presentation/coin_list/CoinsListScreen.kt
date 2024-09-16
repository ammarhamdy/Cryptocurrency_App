package com.am.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.cryptocurrency.R
import com.am.cryptocurrency.domain.model.Coin
import com.am.cryptocurrency.presentation.common.DancingText
import com.am.cryptocurrency.presentation.common.ErrorPage
import com.am.cryptocurrency.presentation.common.ErrorType
import com.am.cryptocurrency.presentation.ui.theme.CryptocurrencyTheme
import okhttp3.internal.toImmutableList

@Composable
fun CoinsListScreen(
    coinListViewModel: CoinListViewModel,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val coinsState by coinListViewModel.coinsState.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        with(coinsState){
            if (isLoading)
                DancingText(stringResource(R.string.loading))
            else if (coins.isNotEmpty())
                CoinsVerticalGrid(
                    coins.toImmutableList(),
                    onClick,
                    Modifier.fillMaxWidth()
                )
            else
                ErrorPage(
                    error ?: ErrorType.UNEXPECTED_ERROR,
                    reload = coinListViewModel::reload
                )
        }
    }
}

@Composable
fun CoinsVerticalGrid(
    coins: List<Coin>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemModifier = Modifier.fillMaxWidth()
    val brush = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiary,
        )
    )
    val brush2 = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary,
        )
    )
    Box(
        modifier = modifier,
    ) {
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.tiny_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.tiny_padding)),
            content = {
                itemsIndexed(items = coins, key = { _, coin -> coin.id }) { index, coin ->
                    CoinItem(
                        coin,
                        { onClick(coin.id) },
                        if (index % 2 == 0) brush2
                        else brush,
                        itemModifier
                    )
                }
            }
        )
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    onClick: () -> Unit,
    color: Brush,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
        shape = CutCornerShape(dimensionResource(R.dimen.small_padding)),
        border = BorderStroke(1.dp, color)
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = coin.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = coin.symbol,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "${coin.rank}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun CoinItemPreview() {
    CryptocurrencyTheme {
        val coin = Coin("1", false, "ammar", 1, "AM")
        val brush = Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.tertiary,
            )
        )
        CoinItem(coin, {}, brush)
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinsListScreenPreview() {
    CryptocurrencyTheme {
        val coins = listOf(
            Coin("1", false, "ammar", 1, "AM"),
            Coin("2", false, "advk fpk2", 10, "AS"),
            Coin("3", false, "asfe 88r ", 141, "ERR"),
            Coin("4", false, "ee l e", 78, "DEF")
        )
        CoinsVerticalGrid(coins, {}, Modifier.fillMaxSize())
    }
}