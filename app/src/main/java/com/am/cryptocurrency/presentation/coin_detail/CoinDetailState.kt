package com.am.cryptocurrency.presentation.coin_detail

import com.am.cryptocurrency.domain.model.CoinDetail
import com.am.cryptocurrency.presentation.common.ErrorType

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null,
    val error: ErrorType? = null
)