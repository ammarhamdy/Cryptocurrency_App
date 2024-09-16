
package com.am.cryptocurrency.presentation.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.am.cryptocurrency.R
import com.am.cryptocurrency.presentation.coin_detail.component.CoinDetailsPage
import com.am.cryptocurrency.presentation.common.DancingText
import com.am.cryptocurrency.presentation.common.ErrorPage
import com.am.cryptocurrency.presentation.common.ErrorType

@Composable
fun CoinDetailsScreen(
    coinDetailViewModel: CoinDetailViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        coinDetailViewModel.coinDetailState?.let {
            with(it.collectAsState().value){
                if (isLoading)
                    DancingText(stringResource(R.string.loading))
                else if (coinDetail != null)
                    CoinDetailsPage(coinDetail)
                else
                    ErrorPage(error ?: ErrorType.UNEXPECTED_ERROR)
            }
        } ?: ErrorPage(ErrorType.UNEXPECTED_ERROR)
    }
}
