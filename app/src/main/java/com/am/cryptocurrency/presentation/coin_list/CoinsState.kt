package com.am.cryptocurrency.presentation.coin_list

import com.am.cryptocurrency.domain.model.Coin
import com.am.cryptocurrency.presentation.common.ErrorType

data class CoinsState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: ErrorType? = null
)
