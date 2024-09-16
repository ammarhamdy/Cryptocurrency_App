package com.am.cryptocurrency.data.repository

import com.am.cryptocurrency.data.remote.CoinApi
import com.am.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Inject


class CoinRepositoryImp @Inject constructor(
    private val coinApi: CoinApi
): CoinRepository {

    override suspend fun getAllCoins() =
        coinApi.getAllCoins()

    override suspend fun getCoin(coinId: String) =
        coinApi.getCoin(coinId)

}