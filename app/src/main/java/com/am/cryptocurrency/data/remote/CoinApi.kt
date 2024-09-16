package com.am.cryptocurrency.data.remote

import com.am.cryptocurrency.data.remote.dto.CoinDetailDto
import com.am.cryptocurrency.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET("/v1/coins")
    suspend fun getAllCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoin(
        @Path("coinId") coinId: String
    ): CoinDetailDto


}