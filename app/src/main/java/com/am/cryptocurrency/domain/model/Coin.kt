package com.am.cryptocurrency.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Coin(
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
)