package com.am.cryptocurrency.domain.repository

import com.am.cryptocurrency.data.remote.dto.CoinDetailDto
import com.am.cryptocurrency.data.remote.dto.CoinDto

interface CoinRepository{

    suspend fun getAllCoins(): List<CoinDto>

    suspend fun getCoin(coinId: String): CoinDetailDto

}