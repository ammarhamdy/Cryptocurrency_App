package com.am.cryptocurrency.domain.use_case

import com.am.cryptocurrency.data.remote.dto.toCoin
import com.am.cryptocurrency.domain.repository.CoinRepository
import com.am.cryptocurrency.presentation.coin_list.CoinsState
import com.am.cryptocurrency.presentation.common.ErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepo: CoinRepository
) {

    operator fun invoke(): Flow<CoinsState> = flow {
        try {
            emit(CoinsState(isLoading = true))
            emit(CoinsState(coins = coinRepo.getAllCoins().map { it.toCoin() }))
        } catch (e: HttpException){
            emit(CoinsState(error = ErrorType.UNEXPECTED_ERROR))
        } catch (e: IOException) {
            emit(CoinsState(error = ErrorType.CONNECTION_ERROR))
        }
    }

}